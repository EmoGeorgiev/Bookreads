package com.Bookreads.dto;

public record LoginResponseDto(
        String username,
        Long id,
        String token) {
}
