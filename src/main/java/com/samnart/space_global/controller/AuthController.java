package com.samnart.space_global.controller;



import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.samnart.space_global.dto.userDTOs.AuthRequestDTO;
import com.samnart.space_global.dto.userDTOs.AuthResponseDTO;
import com.samnart.space_global.dto.userDTOs.UserRegistrationDTO;
import com.samnart.space_global.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registrationDTO));
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO authRequest) {
        return ResponseEntity.ok(authService.login(authRequest));
    }
}