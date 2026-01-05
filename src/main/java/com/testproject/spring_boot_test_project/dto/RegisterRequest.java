package com.testproject.spring_boot_test_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) for user registration requests.
 * Used as the request body for the POST /api/auth/register endpoint.
 * Defined as a Java record for immutability and minimal boilerplate:
 * - Automatically provides constructor, getters, equals(), hashCode(), and toString()
 * - Ensures the data is read-only after creation
 * Validation annotations enforce input requirements:
 * - Email must be present and in valid format
 * - Password must not be blank
 * These constraints are triggered by @Valid in the controller,
 * producing clean error messages via the global exception handler.
 */
public record RegisterRequest(@NotBlank(message = "Email is required")
                             @Email(message = "Invalid email format")
                             String email,

                              @NotBlank(message = "Password is required")
                             String password) {
}
