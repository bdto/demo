package com.example.demo.config;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Solo inserta datos si la BD esta vacia
        if (userRepository.count() == 0) {
            seedUsers();
        }
        if (categoryRepository.count() == 0) {
            seedCategoriesAndProducts();
        }
    }

    private void seedUsers() {
        User admin = User.builder()
                .name("Administrador")
                .email("admin@demo.com")
                .password(passwordEncoder.encode("admin123"))
                .role(Role.ROLE_ADMIN)
                .enabled(true)
                .build();

        User user = User.builder()
                .name("Usuario Demo")
                .email("user@demo.com")
                .password(passwordEncoder.encode("user1234"))
                .role(Role.ROLE_USER)
                .enabled(true)
                .build();

        userRepository.saveAll(List.of(admin, user));
        log.info("Usuarios de prueba creados: admin@demo.com / admin123, user@demo.com / user1234");
    }

    private void seedCategoriesAndProducts() {
        Category electronics = categoryRepository.save(
                Category.builder().name("Electronica").description("Dispositivos electronicos").build()
        );
        Category clothing = categoryRepository.save(
                Category.builder().name("Ropa").description("Prendas de vestir").build()
        );
        Category books = categoryRepository.save(
                Category.builder().name("Libros").description("Libros fisicos y digitales").build()
        );

        productRepository.saveAll(List.of(
                Product.builder().name("Laptop HP").description("Laptop 15 pulgadas 8GB RAM")
                        .price(new BigDecimal("899.99")).stock(50).active(true).category(electronics).build(),
                Product.builder().name("iPhone 15").description("Apple iPhone 15 128GB")
                        .price(new BigDecimal("999.00")).stock(30).active(true).category(electronics).build(),
                Product.builder().name("Camiseta Basica").description("Camiseta algodon talla M")
                        .price(new BigDecimal("19.99")).stock(200).active(true).category(clothing).build(),
                Product.builder().name("Clean Code").description("Robert C. Martin - programacion limpia")
                        .price(new BigDecimal("35.50")).stock(100).active(true).category(books).build()
        ));

        log.info("Categorias y productos de prueba creados");
    }
}