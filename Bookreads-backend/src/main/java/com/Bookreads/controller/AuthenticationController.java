package com.Bookreads.controller;

import com.Bookreads.dto.LoginResponseDto;
import com.Bookreads.dto.SignUpDto;
import com.Bookreads.dto.UserDto;
import com.Bookreads.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/api/signup")
    public ResponseEntity<UserDto> signup(@RequestBody @Valid SignUpDto signUpDto) {
        UserDto userDto = authenticationService.signUp(signUpDto);
        return ResponseEntity.ok()
                .body(userDto);
    }

    @PostMapping("/api/login")
    public ResponseEntity<LoginResponseDto> login(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        LoginResponseDto loginResponseDto = authenticationService.login(authentication);
        return ResponseEntity.ok()
                .body(loginResponseDto);
    }
}
