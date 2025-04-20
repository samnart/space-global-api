package com.samnart.space_global.dto.userDTOs;

import lombok.Data;

@Data
public class UserRegistrationDTO {
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 2, message = "Password must be at least 8 characters")
    private String password;

    private String firstName;
    private String lastName;
    private String phone;
}
