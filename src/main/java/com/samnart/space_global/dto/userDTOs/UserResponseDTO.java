package com.samnart.space_global.dto.userDTOs;

@Data
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
