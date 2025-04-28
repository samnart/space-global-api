package com.samnart.space_global.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.samnart.space_global.model.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, UUID>, JpaSpecificationExecutor<Property> {
    
}
