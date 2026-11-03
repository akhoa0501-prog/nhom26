package com.bookstore.config;

import com.bookstore.repository.AppUserRepository;
import com.bookstore.service.CustomUserDetailsService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(
            CustomUserDetailsService service,
            PasswordEncoder encoder) {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(service);
        provider.setPasswordEncoder(encoder);

        return provider;
    }

    // LUỒNG ĐĂNG NHẬP ADMIN
    @Bean
    @Order(1)
    SecurityFilterChain adminFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider provider) throws Exception {

        http
            .securityMatcher("/admin/**")
            .authenticationProvider(provider)

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/login").permitAll()
                .anyRequest().hasRole("ADMIN")
            )

            .formLogin(form -> form
                .loginPage("/admin/login")
                .loginProcessingUrl("/admin/login")
                .defaultSuccessUrl("/admin", true)
                .failureUrl("/admin/login?error")
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/login?logout")
                .permitAll()
            );

        return http.build();
    }

    // LUỒNG ĐĂNG NHẬP KHÁCH HÀNG
    @Bean
    @Order(2)
    SecurityFilterChain userFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider provider) throws Exception {

        http
            .authenticationProvider(provider)

            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/",
                    "/books/**",
                    "/register",
                    "/login",
                    "/css/**",
                    "/images/**",
                    "/error"
                ).permitAll()

                .requestMatchers("/favorites/**", "/notifications", "/books/*/reviews").authenticated()
                .requestMatchers(
                    "/cart/**",
                    "/checkout",
                    "/orders/**", "/account/**"
                ).authenticated()

                .anyRequest().permitAll()
            )

            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", false)
                .permitAll()
            )

            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }

    // TẠO TÀI KHOẢN ADMIN MẶC ĐỊNH
    @Bean
    CommandLineRunner createAdmin(
            AppUserRepository users,
            PasswordEncoder encoder) {

        return args -> {

            if (users.findByUsername("admin").isEmpty()) {

                com.bookstore.entity.AppUser admin =
                    new com.bookstore.entity.AppUser();

                admin.setUsername("admin");
                admin.setFullName("Quản trị viên");
                admin.setEmail("admin@bookstore.local");
                admin.setPassword(encoder.encode("Admin@123"));
                admin.setRole("ADMIN");

                users.save(admin);
            }
        };
    }
}