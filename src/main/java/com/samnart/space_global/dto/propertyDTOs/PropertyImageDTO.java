package com.samnart.space_global.dto.propertyDTOs;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class PropertyImageDTO {
    private UUID id;
    private String imageUrl;
    private Boolean isPrimary;
    private Integer orderIndex;
    private LocalDateTime createdAt;
}
