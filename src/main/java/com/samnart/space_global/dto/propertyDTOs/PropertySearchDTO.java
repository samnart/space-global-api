package com.samnart.space_global.dto.propertyDTOs;

@Data
public class PropertySearchDTO {
    private String city;
    private String state;
    private String country;
    private PropertyType propertyType;
    private ListingType listingType;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer minBedrooms;
    private Integer minBathrooms;
    private String searchQuery;
}
