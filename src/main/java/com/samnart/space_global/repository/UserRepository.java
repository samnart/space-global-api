package com.samnart.space_global.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samnart.space_global.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    
}