package com.testproject.spring_boot_test_project.repository;

import com.testproject.spring_boot_test_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for User entity.
 * Extends JpaRepository to inherit standard CRUD operations (save, findById, delete, etc.).
 * Custom query method findByEmail is used for:
 * - Looking up users during login
 * - Checking for duplicate emails during registration (if needed)
 * Returns Optional<User> to safely handle cases where the email is not found. If this isn't used the risk is a
 * Null-pointer exception is thrown when no email is found.
 * Spring Data JPA automatically implements this method based on the name.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email address.
     * Used in authentication flow to retrieve user details for password validation and JWT generation.
     *
     * @param email the email to search for
     * @return Optional containing the User if found, empty otherwise
     */
    Optional<User> findByEmail(String email);
}
