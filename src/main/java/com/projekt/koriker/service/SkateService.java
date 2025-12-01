package com.projekt.koriker.service;

import java.util.List;

import org.springframework.stereotype.Service;

//import com.projekt.koriker.data.entity.SkateEntity;
//import com.projekt.koriker.data.repository.SkateRepository;
import com.projekt.koriker.service.dto.SkateDto;

@Service
public interface SkateService {
    List<SkateDto> findAllSkates();
    SkateDto findSkateById(Long id);
    void saveSkate(SkateDto skateDto);
    void deleteSkate(Long id);
}
