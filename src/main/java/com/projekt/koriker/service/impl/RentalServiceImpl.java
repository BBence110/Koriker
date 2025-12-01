package com.projekt.koriker.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.projekt.koriker.data.entity.RentalEntity;
import com.projekt.koriker.data.entity.SkateEntity;
import com.projekt.koriker.data.entity.UserEntity;
import com.projekt.koriker.data.repository.RentalRepository;
import com.projekt.koriker.data.repository.SkateRepository;
import com.projekt.koriker.data.repository.UserRepository;
import com.projekt.koriker.service.RentalService;
import com.projekt.koriker.service.dto.RentalDto;
import com.projekt.koriker.service.mapper.RentalMapper;

import jakarta.transaction.Transactional;

@Service
public class RentalServiceImpl implements RentalService{
    private final RentalRepository rentalRepository;
    private final SkateRepository skateRepository;
    private final UserRepository userRepository;
    private final RentalMapper rentalMapper;

    public RentalServiceImpl(RentalRepository rentalRepository, SkateRepository skateRepository, UserRepository userRepository, RentalMapper rentalMapper) {
        this.rentalRepository = rentalRepository;
        this.skateRepository = skateRepository;
        this.userRepository = userRepository;
        this.rentalMapper = rentalMapper;
    }


    @Override
    public List<RentalDto> findRentalsByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("A felhasználó nem található"));
        
        return rentalRepository.findByUser(user).stream()
                .map(rentalMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public void rentSkate(String username, Long skateId) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("A felhasználó nem található"));

        SkateEntity skate = skateRepository.findById(skateId)
                .orElseThrow(() -> new RuntimeException("Korcsolya nem található"));

        if (!skate.isAvailable()) {
            throw new RuntimeException("A korcsolya már ki lett kölcsönözve!");
        }
        //fogalás
        skate.setAvailable(false);
        skateRepository.save(skate);

        //kolcsonzes 
        RentalEntity rental = new RentalEntity(user, skate, LocalDateTime.now());
        rentalRepository.save(rental);
    }


    @Override
    @Transactional
    public void returnSkate(Long rentalId) {
        RentalEntity rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Kölcsönzés nem található"));

        if (!rental.isActive()) {
            return;
        }
        rental.setActive(false);
        rental.setRentalEnd(LocalDateTime.now());
        rentalRepository.save(rental);
        SkateEntity skate = rental.getSkate();
        skate.setAvailable(true);
        skateRepository.save(skate);
    }
}
