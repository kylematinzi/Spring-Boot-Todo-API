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

//    public AuthResponse register(RegisterRequest request) {
//        var user = User.builder()
//                .email(request.email())
//                .password(passwordEncoder.encode(request.password()))
//                .role(Role.USER)
//                .build();
//        userRepository.save(user);
//        var jwtToken = jwtService.generateToken(user);
//        return new AuthResponse(jwtToken);
//    }
public AuthResponse register(RegisterRequest request) {
    String encodedPassword = passwordEncoder.encode(request.password());
    System.out.println("Encoded password: " + encodedPassword);

    User user = new User();
    user.setEmail(request.email());
    user.setPassword(encodedPassword);
    user.setRole(Role.USER);

    System.out.println("Password after setter: " + user.getPassword());

    userRepository.save(user);

    var jwtToken = jwtService.generateToken(user);
    return new AuthResponse(jwtToken);
}

    public AuthResponse login(LoginRequest request) {
        System.out.println("Login attempt for email: " + request.email());
        System.out.println("Plain password from request: '" + request.password() + "' (length: " + request.password().length() + ")");

        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        System.out.println("Found user with hashed password: " + user.getPassword());

        boolean matches = passwordEncoder.matches(request.password(), user.getPassword());
        System.out.println("Password matches? " + matches);

        if (!matches) {
            throw new BadCredentialsException("Invalid password");
        }

        var jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }
}