package com.Bookreads.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpDto(
        @NotBlank
        @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
        String username,
        @NotBlank
        @Size(min = 3, max = 30, message = "Password must be between 3 and 30 characters")
        String password,
        @Email(message = "Email must be valid")
        @NotBlank
        String email) {
}
