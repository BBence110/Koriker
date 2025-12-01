package com.projekt.koriker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projekt.koriker.service.dto.RentalDto;

@Service
public interface RentalService {
    List<RentalDto> findRentalsByUsername(String username);
    void rentSkate(String username, Long skateId);
    void returnSkate(Long rentalId);
    
}