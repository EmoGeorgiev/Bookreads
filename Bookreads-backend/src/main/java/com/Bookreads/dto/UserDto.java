package com.Bookreads.dto;

import java.util.List;

public record UserDto(
    Long id,
    String username,
    String email,
    List<BookDto> books) {
}
