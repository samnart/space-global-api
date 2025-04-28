package com.samnart.space_global.service;

import com.samnart.space_global.exception.ResourceNotFoundException;
import com.samnart.space_global.model.User;
import com.samnart.space_global.repository.UserRepository;
import com.samnart.space_global.security.UserPrincipal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    
    private final UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return UserPrincipal.create(user);
    }
    
    public UserDetails loadUserById(String userId) {
        UUID userUuid;
        try {
            userUuid = UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException("Invalid user ID format");
        }
        
        User user = userRepository.findById(userUuid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return UserPrincipal.create(user);
    }
}