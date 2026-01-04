package com.testproject.spring_boot_test_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(@NotBlank(message = "Email is required")
                             @Email(message = "Invalid email format")
                             String email,

                              @NotBlank(message = "Password is required")
                             String password) {
}
