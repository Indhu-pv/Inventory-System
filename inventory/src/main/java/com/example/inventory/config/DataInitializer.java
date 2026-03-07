package com.example.inventory.config;

import com.example.inventory.model.Product;
import com.example.inventory.model.User;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, ProductRepository productRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                User admin = new User("admin", passwordEncoder.encode("admin123"), "ADMIN");
                User user = new User("user", passwordEncoder.encode("user123"), "USER");

                userRepository.save(admin);
                userRepository.save(user);
                
                System.out.println("Default users created: admin/admin123, user/user123");
            }

            if (productRepository.count() < 5) {
                // Add 5 to 6 products with high quantity size
                productRepository.save(new Product("Dell XPS 15", "Laptops", 1500.00, 50, 10));
                productRepository.save(new Product("MacBook Pro M2", "Laptops", 2000.00, 45, 10));
                productRepository.save(new Product("iPhone 14 Pro", "Smartphones", 999.00, 100, 20));
                productRepository.save(new Product("Samsung Galaxy S23", "Smartphones", 899.00, 120, 20));
                productRepository.save(new Product("Sony WH-1000XM5", "Accessories", 350.00, 75, 15));
                productRepository.save(new Product("Logitech MX Master 3S", "Accessories", 99.00, 200, 30));
                
                System.out.println("Added 6 default high-quantity products to the database.");
            }
        };
    }
}
