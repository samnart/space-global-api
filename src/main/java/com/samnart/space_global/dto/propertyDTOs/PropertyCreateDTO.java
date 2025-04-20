package com.samnart.space_global.dto.propertyDTOs;

@Data
public class PropertyCreateDTO {
    
    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Property type is required")
    private PropertyType propertyType;

    @NotNull(message = "Listing type is required")
    private ListingType listingType;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;


    private String currency;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Country is required")
    private String country;

    private String postalCode;
    private Integer bedrooms;
    private Integer bathrooms;
    private List<String> amenities;
}
