package com.samnart.space_global.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "properties")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Property {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    // @Enumerated(EnumType.STRING)
    // private PropertyType propertyType;

    // @Enumerated(EnumType.STRING)
    // private ListingType listingType;

    @Column(nullable = false)
    private BigDecimal price;

    private String currency;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private Integer bedrooms;
    private Integer bathrooms;

    @Column(columnDefinition = "jsonb")
    private String amenities;

    private Boolean isAvailable;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<PropertyImage> images;
}

