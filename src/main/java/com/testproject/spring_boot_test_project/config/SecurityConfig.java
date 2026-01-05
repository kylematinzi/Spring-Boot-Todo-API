package com.testproject.spring_boot_test_project.config;

import com.testproject.spring_boot_test_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Central configuration for Spring Security.
 * Defines the security rules for the entire application.
 * Key design decisions:
 * - Stateless authentication, no sessions — uses JWT tokens
 * - CSRF disabled (safe for stateless APIs because I'm using JWT)
 * - Public access to auth endpoints and H2 console
 * - All other endpoints require authentication via valid JWT
 * - JWT filter added to validate tokens on every request
 */
@Configuration
public class SecurityConfig {

    /**
     * Configures the security filter chain.
     * - Disables CSRF (not needed in stateless JWT setup)
     * - Enforces stateless session management
     * - Permits unauthenticated access to registration, login, H2 console, and root
     * - Requires authentication for all other requests
     * - Inserts custom JWT filter before default username/password filter
     * - Disables frame options to allow H2 console in iframe (development only)
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

        return http.build();
    }

    /**
     * Provides BCrypt password encoder for secure password hashing.
     * Automatically used by Spring Security for encoding and matching passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Exposes the AuthenticationManager bean.
     * Required for certain Spring Security features, kept for completeness.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Custom UserDetailsService implementation.
     * Loads user by email "username", from the database.
     * Used by the JWT filter to retrieve user details during token validation.
     */
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
