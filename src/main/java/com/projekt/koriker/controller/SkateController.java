package com.projekt.koriker.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projekt.koriker.service.SkateService;
import com.projekt.koriker.service.dto.SkateDto;

@RestController
@RequestMapping("/api/skates")
@CrossOrigin(origins = "http://localhost:3000") 
public class SkateController {

    private final SkateService skateService;

    public SkateController(SkateService skateService) {
        this.skateService = skateService;
    }

    //korcsolyák kiratása
    @GetMapping
    public ResponseEntity<List<SkateDto>> listSkates() {
        return ResponseEntity.ok(skateService.findAllSkates());
    }

    //törlés,hozzáadás csak admin
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')") 
    public ResponseEntity<Void> addSkate(@RequestBody SkateDto skateDto) {
        skateService.saveSkate(skateDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSkate(@PathVariable Long id) {
        skateService.deleteSkate(id);
        return ResponseEntity.ok().build();
    }
}
