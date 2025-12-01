package com.projekt.koriker.service;

import java.util.List;

//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
//import com.projekt.koriker.data.entity.UserEntity;
//import com.projekt.koriker.data.repository.UserRepository;
import com.projekt.koriker.service.dto.UserDto;

@Service
public interface UserService {
    void registerUser(UserDto userDto);
    UserDto findByUsername(String username);
    List<UserDto> findAllUsers();
}
