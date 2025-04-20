package com.samnart.space_global.dto.propertyDTOs;

@Data
public class PropertyResponseDTO {
    private UUID id;
    private UserResponseDTO owner;
    private String title;
    private String description;
    private PropertyType propertyType;
    private ListingType listingType;
    private BigDecimal price;
    private String currency;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private Integer bedrooms;
    private Integer bathrooms;
    private List<String> amenities;
    private Boolean isAvailable;
    private List<PropertyImageDTO> images;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}