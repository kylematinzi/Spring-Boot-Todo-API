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

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

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