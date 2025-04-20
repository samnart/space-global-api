package com.samnart.space_global.dto.userDTOs;

import lombok.Data;

@Data
public class AuthResponseDTO {
    
    private String token;
    private UserResponseDTO user;
}
