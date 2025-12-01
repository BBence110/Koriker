package com.projekt.koriker.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projekt.koriker.entity.UserEntity;
import com.projekt.koriker.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

public void RegisterUser(UserEntity user){
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    if (user.getRole()==null || user.getRole().isEmpty()){
            user.setRole("USER");
    }
    userRepository.save(user);
    }
}
