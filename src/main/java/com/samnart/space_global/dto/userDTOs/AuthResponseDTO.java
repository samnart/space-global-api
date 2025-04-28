package com.samnart.space_global.dto.userDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    
    private String token;
    private UserResponseDTO user;
}
