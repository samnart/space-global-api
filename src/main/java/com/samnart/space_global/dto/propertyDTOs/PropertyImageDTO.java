package com.samnart.space_global.dto.propertyDTOs;

@Data
public class PropertyImageDTO {
    private UUID id;
    private String imageUrl;
    private Boolean isPrimary;
    private Integer orderIndex;
    private LocalDateTime createdAt;
}
