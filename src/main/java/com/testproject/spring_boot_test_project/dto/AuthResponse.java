package com.testproject.spring_boot_test_project.dto;

/**
 * Data Transfer Object (DTO) representing the response for successful authentication.
 * Returned by both registration and login endpoints.
 * Contains only the JWT token — nothing else is exposed to the client.
 * Using a record ensures immutability and minimal boilerplate:
 * - Automatic constructor, getter, equals(), hashCode(), and toString()
 * Example JSON response:
 * {
 *   "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.xxxxx.yyyyy"
 * }
 */
public record AuthResponse(String token) {
}
