package com.nhpdev.backendservice.configuration;

import com.nhpdev.backendservice.common.Role;
import com.nhpdev.backendservice.common.UserStatus;
import com.nhpdev.backendservice.entity.User;
import com.nhpdev.backendservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Kiểm tra xem đã có admin chưa để tránh tạo trùng lặp mỗi lần restart
            if (userRepository.findUserByUsername("admin").isEmpty()) {
                User admin = User.builder().email("admin")
                        .username("admin")
                        .password(passwordEncoder.encode("admin123"))
                        .role(Role.ADMIN)
                        .status(UserStatus.ACTIVE).build();

                userRepository.save(admin);
                System.out.println(">>> Đã khởi tạo tài khoản admin mặc định.");
            }
        };
    }
}
