package com.Bookreads.service;

import com.Bookreads.dto.LoginResponseDto;
import com.Bookreads.model.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final JwtService jwtService;

    public AuthenticationService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public LoginResponseDto login(Authentication authentication) {
        String username = authentication.getName();
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        Long id = user.getId();
        String token = jwtService.generateToken(authentication);

        return new LoginResponseDto(username, id, token);
    }
}
