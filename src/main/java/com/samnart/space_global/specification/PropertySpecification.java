package com.samnart.space_global.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.samnart.space_global.dto.propertyDTOs.PropertySearchDTO;
import com.samnart.space_global.model.Property;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class PropertySpecification {
    
    public static Specification<Property> getPropertySearchSpecification(PropertySearchDTO searchDTO) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            // Location filters
            if (searchDTO.getCity() != null) {
                predicates.add(criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("city")),
                    searchDTO.getCity().toLowerCase()));
            }
            
            if (searchDTO.getState() != null) {
                predicates.add(criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("state")),
                    searchDTO.getState().toLowerCase()));
            }
            
            if (searchDTO.getCountry() != null) {
                predicates.add(criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("country")),
                    searchDTO.getCountry().toLowerCase()));
            }
            
            // Property type and listing type
            if (searchDTO.getPropertyType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("propertyType"), searchDTO.getPropertyType()));
            }
            
            if (searchDTO.getListingType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("listingType"), searchDTO.getListingType()));
            }
            
            // Price range
            if (searchDTO.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), searchDTO.getMinPrice()));
            }
            
            if (searchDTO.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), searchDTO.getMaxPrice()));
            }
            
            // Rooms
            if (searchDTO.getMinBedrooms() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("bedrooms"), searchDTO.getMinBedrooms()));
            }
            
            if (searchDTO.getMinBathrooms() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("bathrooms"), searchDTO.getMinBathrooms()));
            }
            
            // Search query (title and description)
            if (searchDTO.getSearchQuery() != null && !searchDTO.getSearchQuery().isEmpty()) {
                String searchPattern = "%" + searchDTO.getSearchQuery().toLowerCase() + "%";
                predicates.add(criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), searchPattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), searchPattern)
                ));
            }
            
            // Only show available properties
            predicates.add(criteriaBuilder.isTrue(root.get("isAvailable")));
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}