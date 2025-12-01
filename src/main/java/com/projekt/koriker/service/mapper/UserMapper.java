package com.projekt.koriker.service.mapper;

import org.springframework.stereotype.Component;

import com.projekt.koriker.data.entity.UserEntity;
import com.projekt.koriker.service.dto.UserDto;

@Component
public class UserMapper {

    public UserDto toDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setRole(entity.getRole());
        
        return dto; 
    }

    public UserEntity toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }
        
        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setRole(dto.getRole());
        
        return entity;
    }
    
}
