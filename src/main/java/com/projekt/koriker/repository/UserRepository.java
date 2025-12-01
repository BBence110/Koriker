package com.projekt.koriker.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.projekt.koriker.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Long>{
    UserEntity findByUsername(String username);
    
} 
    

