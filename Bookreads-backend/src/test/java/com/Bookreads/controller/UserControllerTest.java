package com.Bookreads.controller;

import com.Bookreads.dto.SignUpDto;
import com.Bookreads.dto.UpdatePasswordDto;
import com.Bookreads.dto.UserDto;
import com.Bookreads.exception.PasswordsDoNotMatchException;
import com.Bookreads.exception.UserNotFoundException;
import com.Bookreads.exception.UsernameAlreadyExistsException;
import com.Bookreads.service.JpaUserDetailsService;
import com.Bookreads.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.Bookreads.util.ErrorMessages.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private JpaUserDetailsService userDetailsService;

    private UserDto userDto;
    private SignUpDto signUpDto;
    private Long nonExistingId;
    private Long userId;

    @BeforeEach
    public void setUp() {
        String username = "username";
        String email = "username@gmail.com";
        nonExistingId = -1L;
        userId = 1L;

        userDto = new UserDto(userId, username, email, new ArrayList<>());
        signUpDto = new SignUpDto(username, "password", email);
    }

    @Test
    public void shouldReturnNotFoundForNonExistingUserIdWhenGettingUser() throws Exception {
        when(userService.getUser(nonExistingId))
                .thenThrow(new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

        mockMvc.perform(get("/api/users/" + nonExistingId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(USER_NOT_FOUND_MESSAGE));

        verify(userService).getUser(nonExistingId);
    }

    @Test
    public void shouldReturnUserForGivenUserId() throws Exception {
        when(userService.getUser(userId))
                .thenReturn(userDto);

        mockMvc.perform(get("/api/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userDto.id()))
                .andExpect(jsonPath("$.username").value(userDto.username()))
                .andExpect(jsonPath("$.email").value(userDto.email()))
                .andExpect(jsonPath("$.books").value(userDto.books()));


        verify(userService).getUser(userId);
    }

    @Test
    public void shouldReturnUsers() throws Exception {
        UserDto secondUserDto = new UserDto(2L, "secondUser", "secondUser@gmail.com", new ArrayList<>());

        when(userService.getUsers())
                .thenReturn(List.of(userDto, secondUserDto));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(userDto.id()))
                .andExpect(jsonPath("$[0].username").value(userDto.username()))
                .andExpect(jsonPath("$[0].email").value(userDto.email()))
                .andExpect(jsonPath("$[0].books").value(userDto.books()))
                .andExpect(jsonPath("$[1].id").value(secondUserDto.id()))
                .andExpect(jsonPath("$[1].username").value(secondUserDto.username()))
                .andExpect(jsonPath("$[1].email").value(secondUserDto.email()))
                .andExpect(jsonPath("$[1].books").value(secondUserDto.books()));

        verify(userService).getUsers();
    }

    @Test
    public void shouldReturnConflictForExistingUsernameWhenCreatingUser() throws Exception {
        when(userDetailsService.createUser(signUpDto))
                .thenThrow(new UsernameAlreadyExistsException(USERNAME_ALREADY_EXISTS));

        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(USERNAME_ALREADY_EXISTS));

        verify(userDetailsService).createUser(signUpDto);
    }

    @Test
    public void shouldReturnUserWhenCreatingUser() throws Exception {
        when(userDetailsService.createUser(signUpDto))
                .thenReturn(userDto);

        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userDto.id()))
                .andExpect(jsonPath("$.username").value(userDto.username()))
                .andExpect(jsonPath("$.email").value(userDto.email()));

        verify(userDetailsService).createUser(signUpDto);
    }

    @Test
    public void shouldReturnNotFoundForNonExistingUserIdWhenUpdatingUserPassword() throws Exception {
        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto("oldPassword", "newPassword");

        when(userDetailsService.updatePassword(nonExistingId,
                                                updatePasswordDto.oldPassword(),
                                                updatePasswordDto.newPassword()))
                .thenThrow(new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

        mockMvc.perform(put("/api/users/" + nonExistingId + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePasswordDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(USER_NOT_FOUND_MESSAGE));

        verify(userDetailsService).updatePassword(nonExistingId,
                                                    updatePasswordDto.oldPassword(),
                                                    updatePasswordDto.newPassword());
    }

    @Test
    public void shouldReturnBadRequestForInvalidOldPasswordWhenUpdatingUserPassword() throws Exception {
        UpdatePasswordDto invalidOldPasswordDto = new UpdatePasswordDto("pa", "newPassword");

        mockMvc.perform(put("/api/users/" + userId + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidOldPasswordDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.oldPassword").value("Old password must be between 3 and 30 characters"));
    }

    @Test
    public void shouldReturnBadRequestForInvalidNewPasswordWhenUpdatingUserPassword() throws Exception {
        UpdatePasswordDto invalidNewPasswordDto = new UpdatePasswordDto("oldPassword", "ab");

        mockMvc.perform(put("/api/users/" + userId + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidNewPasswordDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.newPassword").value("New password must be between 3 and 30 characters"));
    }

    @Test
    public void shouldReturnBadRequestForWrongPasswordWhenUpdatingPassword() throws Exception {
        UpdatePasswordDto wrongPasswordDto = new UpdatePasswordDto("wrongPassword", "newPassword");

        when(userDetailsService.updatePassword(userId, wrongPasswordDto.oldPassword(), wrongPasswordDto.newPassword()))
                .thenThrow(new PasswordsDoNotMatchException(PASSWORDS_DO_NOT_MATCH));

        mockMvc.perform(put("/api/users/" + userId + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(wrongPasswordDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(PASSWORDS_DO_NOT_MATCH));

        verify(userDetailsService).updatePassword(userId, wrongPasswordDto.oldPassword(), wrongPasswordDto.newPassword());
    }

    @Test
    public void shouldReturnUserWhenUpdatingPassword() throws Exception {
        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto("oldPassword", "newPassword");

        when(userDetailsService.updatePassword(userId, updatePasswordDto.oldPassword(), updatePasswordDto.newPassword()))
                .thenReturn(userDto);

        mockMvc.perform(put("/api/users/" + userId + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePasswordDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDto.id()))
                .andExpect(jsonPath("$.username").value(userDto.username()))
                .andExpect(jsonPath("$.email").value(userDto.email()))
                .andExpect(jsonPath("$.books").value(userDto.books()));

        verify(userDetailsService).updatePassword(userId, updatePasswordDto.oldPassword(), updatePasswordDto.newPassword());
    }

    @Test
    public void shouldReturnNotFoundForNonExistingUserIdWhenUpdatingUser() throws Exception {
        when(userService.updateUser(nonExistingId, userDto))
                .thenThrow(new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

        mockMvc.perform(put("/api/users/" + nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(USER_NOT_FOUND_MESSAGE));

        verify(userService).updateUser(nonExistingId, userDto);
    }

    @Test
    public void shouldReturnNewUserWhenUpdatingUser() throws Exception {
        when(userService.updateUser(userId, userDto))
                .thenReturn(userDto);

        mockMvc.perform(put("/api/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userDto.id()))
                .andExpect(jsonPath("$.username").value(userDto.username()))
                .andExpect(jsonPath("$.email").value(userDto.email()))
                .andExpect(jsonPath("$.books").value(userDto.books()));

        verify(userService).updateUser(userId, userDto);
    }

    @Test
    public void shouldReturnIsBadRequestForInvalidUsernameWhenUpdatingUser() throws Exception {
        UserDto invalidUsernameDto = new UserDto(2L, "ab", "secondUser@gmail.com", new ArrayList<>());

        mockMvc.perform(put("/api/users/" + invalidUsernameDto.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUsernameDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").value("Username must be between 3 and 30 characters"));
    }

    @Test
    public void shouldReturnIsBadRequestForInvalidEmailWhenUpdatingUser() throws Exception {
        UserDto invalidEmailDto = new UserDto(2L, "secondUsername", "secondUser", new ArrayList<>());

        mockMvc.perform(put("/api/users/" + invalidEmailDto.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidEmailDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("Email must be valid"));
    }

    @Test
    public void shouldReturnNotFoundForNonExistingUserIdWhenDeletingUser() throws Exception {
        doThrow(new UserNotFoundException(USER_NOT_FOUND_MESSAGE))
                .when(userService).deleteUser(nonExistingId);

        mockMvc.perform(delete("/api/users/" + nonExistingId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(USER_NOT_FOUND_MESSAGE));

        verify(userService).deleteUser(nonExistingId);
    }

    @Test
    public void shouldDeleteUserAndReturnNoContent() throws Exception {
        doNothing()
                .when(userService).deleteUser(userId);

        mockMvc.perform(delete("/api/users/" + userId))
                .andExpect(status().isNoContent());

        verify(userService).deleteUser(userId);
    }
}
