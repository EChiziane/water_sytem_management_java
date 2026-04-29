package com.api.water_sytem_management_java.infra.config;


import com.api.water_sytem_management_java.models.user.User;
import com.api.water_sytem_management_java.models.user.UserRole;
import com.api.water_sytem_management_java.models.user.UserStatus;
import com.api.water_sytem_management_java.repositories.auth.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   PasswordEncoder passwordEncoder) {
        return args -> {

            if (userRepository.count() == 0) {

                User user = new User();
                user.setName("Admin WSM");
                user.setEmail("admin@wsm.com");
                user.setLogin("admin");
                user.setPhone("+258841234567");
                user.setRole(UserRole.ADMIN);
                user.setStatus(UserStatus.ACTIVE);

                user.setPassword(passwordEncoder.encode("123456"));

                userRepository.save(user);

                System.out.println("✅ USER DEFAULT CRIADO:");
                System.out.println("login: admin");
                System.out.println("password: 123456");
            }
        };
    }
}