package com.testproject.spring_boot_test_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank @Email String email,
                           @NotBlank String password) {

}
