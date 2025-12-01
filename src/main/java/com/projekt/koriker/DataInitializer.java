package com.projekt.koriker;

import com.projekt.koriker.data.entity.SkateEntity;
import com.projekt.koriker.data.entity.UserEntity;
import com.projekt.koriker.data.repository.SkateRepository;
import com.projekt.koriker.data.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final SkateRepository skateRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, SkateRepository skateRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.skateRepository = skateRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Admin :admin-admin
        if (userRepository.findByUsername("admin").isEmpty()) {
            UserEntity admin = new UserEntity();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("admin@test.com");
            admin.setPhoneNumber("36701123344");
            admin.setRole("ROLE_ADMIN");
            userRepository.save(admin);
            System.out.println(">>> ADMIN LÉTREHOZVA: admin / admin");
        }

        // User: user-user
        if (userRepository.findByUsername("user").isEmpty()) {
            UserEntity user = new UserEntity();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user")); 
            user.setEmail("user@test.com");
            user.setPhoneNumber("36209998877");
            user.setRole("ROLE_USER");
            userRepository.save(user);
            System.out.println(">>> USER LÉTREHOZVA: user / user");
        }

        if (skateRepository.count() == 0) {
            skateRepository.save(new SkateEntity(38, "Műkorcsolya", "Fehér", true));
            skateRepository.save(new SkateEntity(42, "Hoki", "Fekete", true));
            skateRepository.save(new SkateEntity(44, "Hoki", "Kék", true));
            System.out.println(">>> KORCSOLYÁK LÉTREHOZVA");
        }
    }
}
