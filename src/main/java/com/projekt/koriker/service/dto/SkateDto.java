package com.projekt.koriker.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SkateDto {
    private Long id;
    private Integer size;
    private String type;
    private String color;
    private boolean available;
}
