package com.samnart.space_global.controller;



import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.samnart.space_global.dto.propertyDTOs.PropertyCreateDTO;
import com.samnart.space_global.dto.propertyDTOs.PropertyResponseDTO;
import com.samnart.space_global.dto.propertyDTOs.PropertySearchDTO;
import com.samnart.space_global.model.User;
import com.samnart.space_global.service.PropertyService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;
    
    @PostMapping
    public ResponseEntity<PropertyResponseDTO> createProperty(
            @Valid @RequestBody PropertyCreateDTO propertyDTO,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(propertyService.createProperty(propertyDTO, user));
    }
    
    @PostMapping("/{propertyId}/images")
    public ResponseEntity<Void> uploadPropertyImages(
            @PathVariable UUID propertyId,
            @RequestParam("images") List<MultipartFile> images) {
        propertyService.addPropertyImages(propertyId, images);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping
    public ResponseEntity<Page<PropertyResponseDTO>> searchProperties(
            PropertySearchDTO searchDTO,
            Pageable pageable) {
        return ResponseEntity.ok(propertyService.searchProperties(searchDTO, pageable));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponseDTO> getProperty(@PathVariable UUID id) {
        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }
}