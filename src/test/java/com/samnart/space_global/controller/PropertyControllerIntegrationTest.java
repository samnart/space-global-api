package com.samnart.space_global.controller;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.samnart.space_global.dto.propertyDTOs.PropertyCreateDTO;
import com.samnart.space_global.model.ListingType;
import com.samnart.space_global.model.Property;
import com.samnart.space_global.model.PropertyType;
import com.samnart.space_global.model.User;
import com.samnart.space_global.model.UserRole;
import com.samnart.space_global.repository.PropertyRepository;
import com.samnart.space_global.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PropertyControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private PropertyRepository propertyRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    private User testUser;
    private Property testProperty;
    
    @BeforeEach
    void setUp() {
        // Create test user
        testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setPasswordHash("hashedPassword");
        testUser.setRole(UserRole.HOST);
        testUser = userRepository.save(testUser);
        
        // Create test property
        testProperty = new Property();
        testProperty.setOwner(testUser);
        testProperty.setTitle("Test Property");
        testProperty.setPropertyType(PropertyType.APARTMENT);
        testProperty.setListingType(ListingType.RENT);
        testProperty.setPrice(BigDecimal.valueOf(1000));
        testProperty.setCity("New York");
        testProperty.setState("NY");
        testProperty.setCountry("USA");
        testProperty.setIsAvailable(true);
        testProperty = propertyRepository.save(testProperty);
    }
    
    @Test
    @WithMockUser(username = "test@example.com", roles = "HOST")
    void createProperty_Success() throws Exception {
        PropertyCreateDTO createDTO = new PropertyCreateDTO();
        createDTO.setTitle("New Apartment");
        createDTO.setPropertyType(PropertyType.APARTMENT);
        createDTO.setListingType(ListingType.RENT);
        createDTO.setPrice(BigDecimal.valueOf(1500));
        createDTO.setAddress("123 Main St");
        createDTO.setCity("Boston");
        createDTO.setState("MA");
        createDTO.setCountry("USA");
        
        mockMvc.perform(post("/api/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Apartment"))
                .andExpect(jsonPath("$.price").value(1500));
    }
    
    @Test
    void searchProperties_Success() throws Exception {
        mockMvc.perform(get("/api/properties")
                .param("city", "New York")
                .param("minPrice", "500")
                .param("maxPrice", "1500"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].title").value("Test Property"));
    }
    
    @Test
    @WithMockUser(username = "test@example.com", roles = "HOST")
    void uploadPropertyImages_Success() throws Exception {
        MockMultipartFile image1 = new MockMultipartFile(
            "images", "image1.jpg", MediaType.IMAGE_JPEG_VALUE, "image1".getBytes());
        MockMultipartFile image2 = new MockMultipartFile(
            "images", "image2.jpg", MediaType.IMAGE_JPEG_VALUE, "image2".getBytes());
        
        mockMvc.perform(multipart("/api/properties/{propertyId}/images", testProperty.getId())
                .file(image1)
                .file(image2))
                .andExpect(status().isOk());
    }
    
    @Test
    void getPropertyById_Success() throws Exception {
        mockMvc.perform(get("/api/properties/{id}", testProperty.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Property"))
                .andExpect(jsonPath("$.propertyType").value("APARTMENT"));
    }
    
    @Test
    void getPropertyById_NotFound() throws Exception {
        mockMvc.perform(get("/api/properties/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}