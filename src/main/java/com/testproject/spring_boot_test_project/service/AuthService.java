package com.testproject.spring_boot_test_project.service;

import com.testproject.spring_boot_test_project.dto.AuthResponse;
import com.testproject.spring_boot_test_project.dto.LoginRequest;
import com.testproject.spring_boot_test_project.dto.RegisterRequest;
import com.testproject.spring_boot_test_project.model.User;
import com.testproject.spring_boot_test_project.model.enums.Role;
import com.testproject.spring_boot_test_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class handling user authentication operations: registration and login.
 * Uses BCrypt for password hashing and JWT for token generation.
 * Manual password validation is used to avoid circular dependencies with AuthenticationManager. This is a common problem
 * with spring.
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * Registers a new user.
     * - Hashes the password with BCrypt before saving.
     * - Assigns default role USER.
     * - Generates and returns a JWT token immediately after registration.
     */
    public AuthResponse register(RegisterRequest request) {
        String encodedPassword = passwordEncoder.encode(request.password());
        User user = new User();
        user.setEmail(request.email());
        user.setPassword(encodedPassword);
        user.setRole(Role.USER);
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }

    /**
     * Authenticates an existing user for login.
     * - Finds user by email, throws exception if not found.
     * - Manually verifies password using BCrypt. Avoids AuthenticationManager to prevent circular dependency.
     * - Returns JWT token on success.
     * Throws BadCredentialsException on invalid password. This will be caught by global handler.
     */
    public AuthResponse login(LoginRequest request) {
        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        boolean matches = passwordEncoder.matches(request.password(), user.getPassword());
        if (!matches) {
            throw new BadCredentialsException("Invalid password");
        }
        var jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }
}