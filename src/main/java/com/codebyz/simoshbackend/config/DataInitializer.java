package com.codebyz.simoshbackend.config;

import com.codebyz.simoshbackend.user.Role;
import com.codebyz.simoshbackend.user.User;
import com.codebyz.simoshbackend.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner seedSuperAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            userRepository.findByUsernameIgnoreCaseOrEmailIgnoreCase("superadmin", "ziyatovzafar98@gmail.com")
                    .orElseGet(() -> {
                        User user = new User();
                        user.setUsername("superadmin");
                        user.setEmail("ziyatovzafar98@gmail.com");
                        user.setRole(Role.SUPER_ADMIN);
                        user.setPasswordHash(passwordEncoder.encode("Zafar123$"));
                        return userRepository.save(user);
                    });
        };
    }
}
