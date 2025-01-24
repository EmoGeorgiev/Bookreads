package com.Bookreads.service;

import com.Bookreads.dto.LoginResponseDto;
import com.Bookreads.dto.SignUpDto;
import com.Bookreads.dto.UserDto;
import com.Bookreads.mapper.UserMapper;
import com.Bookreads.model.BookUser;
import com.Bookreads.model.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final JwtService jwtService;
    private final JpaUserDetailsService userDetailsService;

    public AuthenticationService(JwtService jwtService, JpaUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    public UserDto signUp(SignUpDto signUpDto) {
        BookUser user = UserMapper.signUpDtoToUser(signUpDto);
        BookUser newUser = userDetailsService.createUser(user);
        return UserMapper.userToUserDto(newUser);
    }

    public LoginResponseDto login(Authentication authentication) {
        String username = authentication.getName();
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        Long id = user.getId();
        String token = jwtService.generateToken(authentication);

        return new LoginResponseDto(username, id, token);
    }
}
