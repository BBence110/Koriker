package com.projekt.koriker.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekt.koriker.data.entity.SkateEntity;

public interface SkateRepository extends JpaRepository<SkateEntity,Long>{


    SkateEntity getBySize(int size);
    SkateEntity getByColor(String color);
    
}
