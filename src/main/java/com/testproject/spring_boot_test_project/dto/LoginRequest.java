package com.testproject.spring_boot_test_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) for user login requests.
 * Used as the request body for the POST /api/auth/login endpoint.
 * Defined as a Java record for immutability and concise code:
 * - Provides constructor, getters, equals(), hashCode(), and toString() automatically
 * - Ensures the payload is read-only after creation
 * Validation annotations enforce required fields:
 * - Email must be present and in valid format
 * - Password must not be blank
 * Validation is triggered by @Valid in the controller.
 * On failure, the global exception handler returns clean field-specific errors.
 */
public record LoginRequest(@NotBlank @Email String email,
                           @NotBlank String password) {

}
