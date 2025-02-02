package com.Bookreads.controller;


import com.Bookreads.dto.SignUpDto;
import com.Bookreads.dto.UserDto;
import com.Bookreads.service.JpaUserDetailsService;
import com.Bookreads.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JpaUserDetailsService userDetailsService;

    public UserController(UserService userService, JpaUserDetailsService userDetailsService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        UserDto user = userService.getUser(id);
        return ResponseEntity.ok()
                .body(user);
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = userService.getUsers();
        return ResponseEntity.ok()
                .body(users);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid SignUpDto signUpDto) {
        UserDto userDto = userDetailsService.createUser(signUpDto);
        return ResponseEntity.ok()
                .body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody @Valid UserDto userDto) {
        UserDto updatedUser = userService.updateUser(id, userDto);
        return ResponseEntity.ok()
                .body(updatedUser);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<UserDto> updatePassword(@PathVariable Long id, String oldPassword, String newPassword) {
        UserDto updatedUser = userDetailsService.updatePassword(id, oldPassword, newPassword);
        return ResponseEntity.ok()
                .body(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent()
                .build();
    }
}
