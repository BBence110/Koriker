package com.projekt.koriker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projekt.koriker.entity.SkateEntity;
import com.projekt.koriker.repository.SkateRepository;

@Service
public class SkateService {
    private final SkateRepository skateRepository;
    
    public SkateService(SkateRepository skateRepository){
        this.skateRepository=skateRepository;
    }
    public List<SkateEntity> findAllSkates() {
        return skateRepository.findAll();
    }
    public SkateEntity findSkateById(Long id) {
        return skateRepository.findById(id).orElseThrow(() -> new RuntimeException("Nincs ilyen korcsolya"));
    }
    public void saveSkate(SkateEntity skate) {
        if (skate.getId() == null) {
            skate.setAvailable(true);
        }
        skateRepository.save(skate);
    }
        public void deleteSkate(Long id) {
            skateRepository.deleteById(id);
        }
}
