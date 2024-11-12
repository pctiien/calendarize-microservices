package com.example.authservice;

import com.example.authservice.entity.AuthProvider;
import com.example.authservice.entity.Role;
import com.example.authservice.entity.RoleName;
import com.example.authservice.entity.User;
import com.example.authservice.repository.RoleRepository;
import com.example.authservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Role userRole = roleRepository.findRoleByName(RoleName.USER).orElseGet(()->roleRepository.save(new Role(RoleName.USER)));
            Role adminRole = roleRepository.findRoleByName(RoleName.ADMIN).orElseGet(()->roleRepository.save(new Role(RoleName.ADMIN)));
            String adminName = "admin";
            if (userRepository.findByName(adminName).isEmpty()) {
                User adminUser = new User();
                adminUser.setName(adminName);
                adminUser.setEmail("admin@example.com");
                adminUser.setPassword(passwordEncoder.encode("admin"));
                adminUser.setAuthProvider(AuthProvider.local);
                adminUser.assignRole(adminRole);
                adminUser.assignRole(userRole);

                userRepository.save(adminUser);
                System.out.println("Admin user created with default credentials.");
            } else {
                System.out.println("Admin user already exists.");
            }
        };
    }}
