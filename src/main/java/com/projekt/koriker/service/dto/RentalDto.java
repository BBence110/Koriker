package com.projekt.koriker.service.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RentalDto {
    private Long id;
    private UserDto user;
    private SkateDto skate;
    private LocalDateTime rentalStart;
    private LocalDateTime rentalEnd;
    private boolean active;

    
}
