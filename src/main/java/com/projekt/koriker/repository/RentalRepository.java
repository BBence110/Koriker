package com.projekt.koriker.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projekt.koriker.entity.RentalEntity;

@Repository
public interface RentalRepository extends JpaRepository<RentalEntity,Long> {
    
   
}
