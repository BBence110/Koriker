package com.projekt.koriker.service.mapper;

import org.springframework.stereotype.Component;

import com.projekt.koriker.data.entity.RentalEntity;
import com.projekt.koriker.service.dto.RentalDto;

@Component
public class RentalMapper {
    private final UserMapper userMapper;
    private final SkateMapper skateMapper;
    public RentalMapper(UserMapper userMapper, SkateMapper skateMapper) {
        this.userMapper = userMapper;
        this.skateMapper = skateMapper;
    }

    public RentalDto toDto(RentalEntity entity) {
        if (entity == null) {
            return null;
        }
        RentalDto dto = new RentalDto();
        dto.setId(entity.getId());
        dto.setUser(userMapper.toDto(entity.getUser())); 
        dto.setSkate(skateMapper.toDto(entity.getSkate())); 
        dto.setRentalStart(entity.getRentalStart());
        dto.setRentalEnd(entity.getRentalEnd());
        dto.setActive(entity.isActive());
        return dto;
    }
}
