package com.projekt.koriker.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projekt.koriker.entity.RentalEntity;
import com.projekt.koriker.entity.UserEntity;

@Repository
public interface RentalRepository extends JpaRepository<RentalEntity,Long> {

    List<RentalEntity> findByUser(UserEntity user);
    
   
}
