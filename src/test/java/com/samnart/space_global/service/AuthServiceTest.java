package com.samnart.space_global.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.samnart.space_global.dto.userDTOs.AuthRequestDTO;
import com.samnart.space_global.dto.userDTOs.AuthResponseDTO;
import com.samnart.space_global.dto.userDTOs.UserRegistrationDTO;
import com.samnart.space_global.exception.UnauthorizedException;
import com.samnart.space_global.exception.UserAlreadyExistsException;
import com.samnart.space_global.model.User;
import com.samnart.space_global.model.UserRole;
import com.samnart.space_global.repository.UserRepository;
import com.samnart.space_global.security.JwtTokenProvider;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    
    @InjectMocks
    private AuthService authService;
    
    private UserRegistrationDTO registrationDTO;
    private AuthRequestDTO authRequestDTO;
    private User user;
    
    @BeforeEach
    void setUp() {
        registrationDTO = new UserRegistrationDTO();
        registrationDTO.setEmail("test@example.com");
        registrationDTO.setPassword("password123");
        registrationDTO.setFirstName("John");
        registrationDTO.setLastName("Doe");
        
        authRequestDTO = new AuthRequestDTO();
        authRequestDTO.setEmail("test@example.com");
        authRequestDTO.setPassword("password123");
        
        user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("test@example.com");
        user.setPasswordHash("hashedPassword");
        user.setRole(UserRole.GUEST);
    }
    
    @Test
    void register_Success() {
        when(userRepository.existsByEmail(registrationDTO.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(registrationDTO.getPassword())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtTokenProvider.generateToken(user)).thenReturn("jwt-token");
        
        AuthResponseDTO response = authService.register(registrationDTO);
        
        assertThat(response.getToken()).isEqualTo("jwt-token");
        assertThat(response.getUser().getEmail()).isEqualTo(user.getEmail());
        verify(userRepository).save(any(User.class));
    }
    
    @Test
    void register_UserAlreadyExists() {
        when(userRepository.existsByEmail(registrationDTO.getEmail())).thenReturn(true);
        
        assertThatThrownBy(() -> authService.register(registrationDTO))
            .isInstanceOf(UserAlreadyExistsException.class)
            .hasMessage("Email already registered");
            
        verify(userRepository, never()).save(any(User.class));
    }
    
    @Test
    void login_Success() {
        when(userRepository.findByEmail(authRequestDTO.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(authRequestDTO.getPassword(), user.getPasswordHash())).thenReturn(true);
        when(jwtTokenProvider.generateToken(user)).thenReturn("jwt-token");
        
        AuthResponseDTO response = authService.login(authRequestDTO);
        
        assertThat(response.getToken()).isEqualTo("jwt-token");
        assertThat(response.getUser().getEmail()).isEqualTo(user.getEmail());
    }
    
    @Test
    void login_InvalidCredentials() {
        when(userRepository.findByEmail(authRequestDTO.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(authRequestDTO.getPassword(), user.getPasswordHash())).thenReturn(false);
        
        assertThatThrownBy(() -> authService.login(authRequestDTO))
            .isInstanceOf(UnauthorizedException.class)
            .hasMessage("Invalid email or password");
    }
}