package com.Bookreads.service;

import com.Bookreads.dto.SignUpDto;
import com.Bookreads.dto.UserDto;
import com.Bookreads.exception.PasswordsDoNotMatchException;
import com.Bookreads.exception.UserNotFoundException;
import com.Bookreads.exception.UsernameAlreadyExistsException;
import com.Bookreads.mapper.UserMapper;
import com.Bookreads.model.BookUser;
import com.Bookreads.model.SecurityUser;
import com.Bookreads.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JpaUserDetailsServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private JpaUserDetailsService userDetailsService;

    private SignUpDto signUpDto;
    private BookUser user;
    private Long userId;
    private String username;
    private String oldPassword;
    private String newPassword;
    private String userPassword;

    @BeforeEach
    void setUp() {
        userId = 1L;
        username = "username";
        oldPassword = "oldPassword";
        newPassword = "newPassword";

        signUpDto = new SignUpDto(username, oldPassword, "username@gmail.com");
        user = UserMapper.signUpDtoToUser(signUpDto);
        user.setId(userId);
        userPassword = user.getPassword();
    }

    @Test
    public void shouldThrowUsernameAlreadyExistsExceptionForExistingUsernameWhenCreatingUser() {
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));

        assertThrows(UsernameAlreadyExistsException.class, () -> userDetailsService.createUser(signUpDto));

        verify(userRepository).findByUsername(username);
    }

    @Test
    public void shouldReturnUserDtoForForNonExistingUsernameWhenCreatingUser() {
        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.empty());

        when(userRepository.save(any(BookUser.class)))
                .thenReturn(user);

        when(passwordEncoder.encode(userPassword))
                .thenReturn(any(String.class));

        UserDto result = userDetailsService.createUser(signUpDto);

        assertNotNull(result);
        assertEquals(user.getId(), result.id());
        assertEquals(user.getUsername(), result.username());
        assertEquals(user.getEmail(), result.email());

        verify(userRepository).findByUsername(username);
        verify(userRepository).save(any(BookUser.class));
        verify(passwordEncoder).encode(userPassword);
    }

    @Test
    public void shouldThrowUserNotFoundExceptionForNonExistingUserWhenUpdatingPassword() {
        Long nonExistingId = -1L;

        when(userRepository.findById(nonExistingId))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userDetailsService.updatePassword(nonExistingId, signUpDto.password(), newPassword));

        verify(userRepository).findById(nonExistingId);
    }

    @Test
    public void shouldThrowPasswordsDoNotMatchExceptionForNonMatchingOldPasswordAndUserPasswordWhenUpdatingPassword() {
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(oldPassword, userPassword))
                .thenReturn(false);

        assertThrows(PasswordsDoNotMatchException.class, () -> userDetailsService.updatePassword(userId, oldPassword, newPassword));

        verify(userRepository).findById(userId);
        verify(passwordEncoder).matches(oldPassword, userPassword);
    }

    @Test
    public void shouldReturnUserDtoForMatchingOldPasswordAndUserPasswordWhenUpdatingPassword() {
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(oldPassword, userPassword))
                .thenReturn(true);

        when(userRepository.save(any(BookUser.class)))
                .thenReturn(user);

        UserDto result = userDetailsService.updatePassword(userId, oldPassword, newPassword);

        assertNotNull(result);
        assertEquals(user.getId(), result.id());
        assertEquals(user.getUsername(), result.username());
        assertEquals(user.getEmail(), result.email());

        verify(userRepository).findById(userId);
        verify(passwordEncoder).matches(oldPassword, userPassword);
        verify(userRepository).save(any(BookUser.class));
    }

    @Test
    public void shouldThrowUsernameNotFoundExceptionForNonExistingUsernameWhenLoadingByUsername() {
        when(userRepository.findByUsername(any(String.class)))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));

        verify(userRepository).findByUsername(any(String.class));
    }

    @Test
    public void shouldReturnUserDetailsForExistingUsernameWhenLoadingByUsername() {
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));

        UserDetails result = userDetailsService.loadUserByUsername(username);

        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getPassword(), result.getPassword());

        verify(userRepository).findByUsername(username);
    }
}
