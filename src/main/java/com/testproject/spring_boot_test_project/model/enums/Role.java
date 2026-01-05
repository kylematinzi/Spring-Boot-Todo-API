package com.testproject.spring_boot_test_project.model.enums;

/**
 * Enumeration of user roles in the system.
 * Using an enum provides type-safety and prevents invalid or misspelled role values
 * (example: "admiin", "Admin", "user ") that could occur with plain strings.
 * Roles are used by Spring Security for authorization:
 * - USER: standard authenticated user (can manage their own todos)
 * - ADMIN: future administrative role (full system access)
 * ADMIN was not used in this program. It is there for future use.
 * The enum values are stored as strings in the database via @Enumerated(EnumType.STRING),
 * making them human-readable and safe for future extension.
 */
public enum Role {
    USER, // regular user, can only access their todos
    ADMIN // Administrator, can access all todos
}