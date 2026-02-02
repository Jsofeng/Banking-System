package com.example.bankingsystemsb.config;

import com.example.bankingsystemsb.model.User;
import com.example.bankingsystemsb.repository.UserJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initUsers(UserJpaRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if(userRepository.findByUsername("Jonathan").isEmpty()) {
                User user = new  User();
                user.setUsername("Jonathan");
                user.setPassword(passwordEncoder.encode("password123"));
                user.setEmail("jonathan@gmail.com");
                user.setActive(true);
                userRepository.save(user);
                System.out.println("Created default User Jonathan w encoded password");
            }
        };
    }
}

