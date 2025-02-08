package com.Bookreads.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserDto(
    Long id,
    @NotBlank
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    String username,
    @Email(message = "Email must be valid")
    @NotBlank
    String email,
    List<BookDto> books) {
}
