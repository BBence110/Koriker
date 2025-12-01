package com.projekt.koriker.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.projekt.koriker.service.RentalService;
import com.projekt.koriker.service.dto.RentalDto;

@RestController
@RequestMapping("/api/rentals")
@CrossOrigin(origins = "http://localhost:3000")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/rent/{skateId}")
    public ResponseEntity<?> rentSkate(@PathVariable Long skateId) {
        // JWT Filterből tudjuk a bejelentkezetzt felhasznalot
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        try {
            rentalService.rentSkate(username, skateId);
            return ResponseEntity.ok("Sikeres kölcsönzés!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //saját kölcsönzések lekérése
    @GetMapping("/my")
    public ResponseEntity<List<RentalDto>> myRentals() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return ResponseEntity.ok(rentalService.findRentalsByUsername(username));
    }

    //visszavétel
    @PostMapping("/return/{rentalId}")
    public ResponseEntity<?> returnSkate(@PathVariable Long rentalId) {
        try {
            rentalService.returnSkate(rentalId);
            return ResponseEntity.ok("Sikeres visszavétel!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
