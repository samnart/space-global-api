package com.samnart.space_global.dto.userDTOs;

import java.time.LocalDateTime;
import java.util.UUID;

import com.samnart.space_global.model.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String profileImage;
    private UserRole role;
    private LocalDateTime createdAt;
}
