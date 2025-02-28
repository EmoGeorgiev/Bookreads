package com.Bookreads.dto;

import com.Bookreads.enums.Bookshelf;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record BookDto(
        Long id,
        @NotBlank(message = "Title cannot be blank")
        @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
        String title,
        @NotBlank(message = "Author cannot be blank")
        @Size(min = 1, max = 100, message = "Author must be between 1 and 100 characters")
        String author,
        @Min(value = 1, message = "Page count cannot be less than 1")
        Integer pageCount,
        @Min(value = 1, message = "Rating cannot be less than 1")
        @Max(value = 5, message = "Rating cannot be more than 5")
        Integer rating,
        String review,
        LocalDate dateRead,
        Bookshelf bookshelf,
        @NotNull(message = "User id cannot be null")
        Long userId) {
}
