package com.samnart.space_global.dto.propertyDTOs;

import java.math.BigDecimal;

import com.samnart.space_global.model.ListingType;
import com.samnart.space_global.model.PropertyType;

import lombok.Data;

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
