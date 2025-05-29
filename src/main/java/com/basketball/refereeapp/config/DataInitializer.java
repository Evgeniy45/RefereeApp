package com.basketball.refereeapp.config;

import com.basketball.refereeapp.model.Role;
import com.basketball.refereeapp.model.User;
import com.basketball.refereeapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User("admin", passwordEncoder.encode("adminpass"), Role.ADMIN);
                userRepository.save(admin);
            }

            if (userRepository.findByUsername("referee1").isEmpty()) {
                User referee = new User("referee1", passwordEncoder.encode("refpass"), Role.REFEREE);
                userRepository.save(referee);
            }
        };
    }
}
