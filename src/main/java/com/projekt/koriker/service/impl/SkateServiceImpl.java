package com.projekt.koriker.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.projekt.koriker.data.entity.SkateEntity;
import com.projekt.koriker.data.repository.SkateRepository;
import com.projekt.koriker.service.SkateService;
import com.projekt.koriker.service.dto.SkateDto;
import com.projekt.koriker.service.mapper.SkateMapper;

@Service
public class SkateServiceImpl implements SkateService{
    private final SkateRepository skateRepository;
    private final SkateMapper skateMapper;

    public SkateServiceImpl(SkateRepository skateRepository, SkateMapper skateMapper) {
        this.skateRepository = skateRepository;
        this.skateMapper = skateMapper;
    }
    @Override
    public List<SkateDto> findAllSkates() {
        return skateRepository.findAll().stream()
                .map(skateMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public SkateDto findSkateById(Long id) {
        SkateEntity skate = skateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Korcsolya nem található"));
        return skateMapper.toDto(skate);
    }
    @Override
    public void saveSkate(SkateDto skateDto) {
        SkateEntity skate = skateMapper.toEntity(skateDto);
        if (skate.getId() == null) {
            skate.setAvailable(true);
        }
        skateRepository.save(skate);
    }

    @Override
    public void deleteSkate(Long id) {
        skateRepository.deleteById(id);
    }
    
}
