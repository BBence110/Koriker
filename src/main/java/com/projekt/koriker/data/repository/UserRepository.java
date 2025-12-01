package com.projekt.koriker.data.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.projekt.koriker.data.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Long>{
    UserEntity findByUsername(String username);
    
} 
    

