package com.projekt.koriker.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projekt.koriker.data.entity.UserEntity;
import com.projekt.koriker.data.repository.UserRepository;
import com.projekt.koriker.service.UserService;
import com.projekt.koriker.service.dto.UserDto;
import com.projekt.koriker.service.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }
    
    public void registerUser (UserDto userDto){
        UserEntity user=userMapper.toEntity(userDto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if( user.getRole()==null||user.getRole().isEmpty()){
            user.setRole("USER");
        }
        userRepository.save(user);
    }
    @Override
    public UserDto findByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        return userMapper.toDto(user);
    }
    @Override
    public java.util.List<UserDto> findAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(java.util.stream.Collectors.toList());
    }
    
}
