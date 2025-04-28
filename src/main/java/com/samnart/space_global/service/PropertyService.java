package com.samnart.space_global.service;

// service/PropertyService.java


import com.fasterxml.jackson.databind.ObjectMapper;
import com.samnart.space_global.dto.propertyDTOs.PropertyCreateDTO;
import com.samnart.space_global.dto.propertyDTOs.PropertyResponseDTO;
import com.samnart.space_global.dto.propertyDTOs.PropertySearchDTO;
import com.samnart.space_global.exception.ResourceNotFoundException;
import com.samnart.space_global.model.Property;
import com.samnart.space_global.model.PropertyImage;
import com.samnart.space_global.model.User;
import com.samnart.space_global.repository.PropertyImageRepository;
import com.samnart.space_global.repository.PropertyRepository;
import com.samnart.space_global.specification.PropertySpecification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final PropertyImageRepository propertyImageRepository;
    private final StorageService storageService;
    private final ObjectMapper objectMapper;
    
    @Transactional
    public PropertyResponseDTO createProperty(PropertyCreateDTO propertyDTO, User owner) {
        Property property = new Property();
        property.setOwner(owner);
        property.setTitle(propertyDTO.getTitle());
        property.setDescription(propertyDTO.getDescription());
        property.setPropertyType(propertyDTO.getPropertyType());
        property.setListingType(propertyDTO.getListingType());
        property.setPrice(propertyDTO.getPrice());
        property.setCurrency(propertyDTO.getCurrency() != null ? propertyDTO.getCurrency() : "USD");
        property.setAddress(propertyDTO.getAddress());
        property.setCity(propertyDTO.getCity());
        property.setState(propertyDTO.getState());
        property.setCountry(propertyDTO.getCountry());
        property.setPostalCode(propertyDTO.getPostalCode());
        property.setBedrooms(propertyDTO.getBedrooms());
        property.setBathrooms(propertyDTO.getBathrooms());
        
        if (propertyDTO.getAmenities() != null) {
            try {
                property.setAmenities(objectMapper.writeValueAsString(propertyDTO.getAmenities()));
            } catch (Exception e) {
                throw new RuntimeException("Error processing amenities", e);
            }
        }
        
        property.setIsAvailable(true);
        Property savedProperty = propertyRepository.save(property);
        
        return mapToPropertyResponseDTO(savedProperty);
    }
    
    @Transactional
    public void addPropertyImages(UUID propertyId, List<MultipartFile> images) {
        Property property = propertyRepository.findById(propertyId)
            .orElseThrow(() -> new ResourceNotFoundException("Property not found"));
            
        for (int i = 0; i < images.size(); i++) {
            MultipartFile image = images.get(i);
            String imageUrl = storageService.uploadFile(image, "properties/" + propertyId);
            
            PropertyImage propertyImage = new PropertyImage();
            propertyImage.setProperty(property);
            propertyImage.setImageUrl(imageUrl);
            propertyImage.setIsPrimary(i == 0);
            propertyImage.setOrderIndex(i);
            
            propertyImageRepository.save(propertyImage);
        }
    }
    
    public Page<PropertyResponseDTO> searchProperties(PropertySearchDTO searchDTO, Pageable pageable) {
        return propertyRepository.findAll(PropertySpecification.getPropertySearchSpecification(searchDTO), pageable)
            .map(this::mapToPropertyResponseDTO);
    }
    
    public PropertyResponseDTO getPropertyById(UUID id) {
        Property property = propertyRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Property not found"));
        return mapToPropertyResponseDTO(property);
    }
    
    private PropertyResponseDTO mapToPropertyResponseDTO(Property property) {
        PropertyResponseDTO dto = new PropertyResponseDTO();
        // Map all fields from property to dto
        // Including mapping property images and owner details
        return dto;
    }
}