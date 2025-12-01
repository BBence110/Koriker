package com.projekt.koriker.data.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekt.koriker.data.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Long>{
    Optional<UserEntity> findByUsername(String username);
    
} 
    

