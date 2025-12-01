package com.projekt.koriker.data.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projekt.koriker.data.entity.RentalEntity;
import com.projekt.koriker.data.entity.UserEntity;

@Repository
public interface RentalRepository extends JpaRepository<RentalEntity,Long> {

    List<RentalEntity> findByUser(UserEntity user);
    
   
}
