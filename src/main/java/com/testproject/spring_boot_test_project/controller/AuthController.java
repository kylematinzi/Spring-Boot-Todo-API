package com.testproject.spring_boot_test_project.controller;

import com.testproject.spring_boot_test_project.dto.AuthResponse;
import com.testproject.spring_boot_test_project.dto.LoginRequest;
import com.testproject.spring_boot_test_project.dto.RegisterRequest;
import com.testproject.spring_boot_test_project.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller handling authentication endpoints.
 * Public endpoints for user registration and login. JWT not required.
 * Both endpoints are intentionally open (@RequestMapping("/api/auth/**") permitted in SecurityConfig)
 * to allow new users to sign up and existing users to obtain a JWT token.
 * Uses @Valid to trigger validation on the incoming DTOs (RegisterRequest/LoginRequest).
 * Returns AuthResponse containing the JWT token on success.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Registers a new user.
     * - Validates input: email format, required fields.
     * - Hashes password via AuthService.
     * - Generates and returns a JWT token immediately.
     * HTTP 200 OK with token on success.
     * Validation errors return 400 via global handler.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Authenticates an existing user and returns a JWT token.
     * - Validates input fields.
     * - Verifies credentials via AuthService.
     * - Returns token on success.
     * Returns 401 Unauthorized (via global handler) on invalid credentials.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
