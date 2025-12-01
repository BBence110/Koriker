package com.projekt.koriker.service.mapper;

import org.springframework.stereotype.Component;

import com.projekt.koriker.data.entity.SkateEntity;
import com.projekt.koriker.service.dto.SkateDto;

@Component
public class SkateMapper {
    public SkateDto toDto(SkateEntity entity) {
        if (entity == null) {
            return null;
        }
        return new SkateDto(
            entity.getId(),
            entity.getSize(),
            entity.getType(),
            entity.getColor(),
            entity.isAvailable()
        );
    }
    public SkateEntity toEntity(SkateDto dto){
        if (dto==null){
            return null;
        }
        return new SkateEntity(
            dto.getSize(),
            dto.getType(),
            dto.getColor(),
            dto.isAvailable()
        );
    }

    
}
