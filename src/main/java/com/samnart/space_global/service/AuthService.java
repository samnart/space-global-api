package com.samnart.space_global.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.samnart.space_global.dto.userDTOs.AuthRequestDTO;
import com.samnart.space_global.dto.userDTOs.AuthResponseDTO;
import com.samnart.space_global.dto.userDTOs.UserRegistrationDTO;
import com.samnart.space_global.dto.userDTOs.UserResponseDTO;
import com.samnart.space_global.model.User;
import com.samnart.space_global.model.UserRole;
import com.samnart.space_global.repository.UserRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    // private final JwtTokenProvider jwtTokenProvider;

    public AuthResponseDTO register(UserRegistrationDTO registrationDTO) {
        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered!");
        }

        User user = new User();
        user.setEmail(registrationDTO.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setPhoneNumber(registrationDTO.getPhone());
        user.setRole(UserRole.GUEST);

        User savedUser = userRepository.save(user);
        String token = "Samuel";
        // String token = jwtTokenProvider.generateToken(savedUser);

        return new AuthResponseDTO(token, mapToUserResponseDTO(savedUser));
    }

    public AuthResponseDTO login(AuthRequestDTO authRequest) {
        User user = userRepository.findByEmail(authRequest.getEmail())
            .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));
        
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        String token = jwtTokenProvider.generateToken(user);
        return new AuthResponseDTO(token, mapToUserResponseDTO(user));
    }

    private UserResponseDTO mapToUserResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhone(user.getPhoneNumber());
        dto.setProfileImage(user.getProfileImage());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }

}