package com.projekt.koriker.controller;

import com.projekt.koriker.security.JwtUtils;
import com.projekt.koriker.service.UserService;
import com.projekt.koriker.service.dto.LoginRequest;
import com.projekt.koriker.service.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        if (authentication.isAuthenticated()) {
            var userDetails = (org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal();
            
            //String token = jwtUtils.generateToken(loginRequest.getUsername());
            
            UserDto user = userService.findByUsername(loginRequest.getUsername());
            
            String token = jwtUtils.generateToken(loginRequest.getUsername(),
            java.util.Map.of(
                "isEnabled", userDetails.isEnabled(),
                "getUsername", userDetails.getUsername(),
                "isAccountNonLocked", userDetails.isAccountNonLocked(),
                "isAccountNonExpired", userDetails.isAccountNonExpired(),
                "isCredentialsNonExpired", userDetails.isCredentialsNonExpired(),
                "role", user.getRole()
            ));


            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("role", user.getRole());
            response.put("username", user.getUsername());
            
    
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Hibás felhasználónév vagy jelszó");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        try {
            userService.registerUser(userDto);
            return ResponseEntity.ok("Sikeres regisztráció");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Hiba: " + e.getMessage());
        }
    }
}