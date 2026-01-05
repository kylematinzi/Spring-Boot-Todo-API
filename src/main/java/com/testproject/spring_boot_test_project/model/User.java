package com.testproject.spring_boot_test_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.testproject.spring_boot_test_project.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * This class creates a User entity.
 * Implements Spring Security's UserDetails interface so it can be used directly for authentication.
 * Each user must have a unique email and BCrypt-hashed password.
 * A BCrypt password is a plain text password stored using the BCrypt hashing algorithm.
 * The @JsonIgnore on password prevents it from being leaked in API responses.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id // this tells Spring it's an ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    // @JsonIgnore from Jackson tells the JSON serializer to completely exclude the annotated field from the output.
    // This keeps the hashed password from being sent in any API responses.
    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;


    /**
     * Returns the authorities (roles) granted to the user.
     * Spring Security uses this to enforce @PreAuthorize or method-level security.
     * We prefix with "ROLE_" as required by Spring's convention.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    /**
     * Returns the password used to authenticate the user.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username used to authenticate the user.
     * In this system, I use email as the username.
     */
    @Override
    public String getUsername() {
        return email;
    }

    // Standard account status methods — all return true for simplicity in this project.
    // In a production app I could tie these to specific field to see if an account was locked or expired.
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
