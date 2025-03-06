package com.Bookreads.service;

import com.Bookreads.dto.UserDto;
import com.Bookreads.exception.UserNotFoundException;
import com.Bookreads.mapper.UserMapper;
import com.Bookreads.model.BookUser;
import com.Bookreads.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    private BookUser user;
    private UserDto userDto;
    private Long nonExistingId;
    private Long userId;

    @BeforeEach
    void setUp() {
        nonExistingId = -1L;
        userId = 1L;

        user = new BookUser();
        user.setId(userId);
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("username@gmail.com");
        user.setBooks(new ArrayList<>());

        userDto = UserMapper.userToUserDto(user);
    }

    @Test
    public void shouldReturnTrueForExistingUserId() {
        when(userRepository.existsById(userId))
                .thenReturn(true);

        boolean result = userService.existsById(userId);

        assertTrue(result);

        verify(userRepository).existsById(userId);
    }

    @Test
    public void shouldReturnFalseForNonExistingUserId() {
        when(userRepository.existsById(nonExistingId))
                .thenReturn(false);

        boolean result = userService.existsById(nonExistingId);

        assertFalse(result);

        verify(userRepository).existsById(nonExistingId);
    }

    @Test
    public void shouldThrowUserNotFoundExceptionForNonExistingUserIdWhenGettingUserEntity() {
        when(userRepository.findById(nonExistingId))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserEntity(nonExistingId));

        verify(userRepository).findById(nonExistingId);
    }

    @Test
    public void shouldReturnUserForExistingUserIdWhenGettingUserEntity() {
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        BookUser result = userService.getUserEntity(userId);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getBooks(), result.getBooks());

        verify(userRepository).findById(userId);
    }

    @Test
    public void shouldReturnUserDtoForExistingUserIdWhenGettingUser() {
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        UserDto result = userService.getUser(userId);

        assertNotNull(result);
        assertEquals(userDto, result);

        verify(userRepository).findById(userId);
    }

    @Test
    public void shouldReturnEmptyListOfUserDtoWhenGettingUsers() {
        when(userRepository.findAll())
                .thenReturn(new ArrayList<>());

        List<UserDto> result = userService.getUsers();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(userRepository).findAll();
    }

    @Test
    public void shouldReturnListOfUserDtoWhenGettingUsers() {
        BookUser secondUser = new BookUser();
        secondUser.setId(2L);
        secondUser.setUsername("secondUsername");
        secondUser.setPassword("secondPassword");
        secondUser.setEmail("secondUsername@gmail.com");
        secondUser.setBooks(new ArrayList<>());

        List<BookUser> users = List.of(user, secondUser);
        List<UserDto> expected = users.stream()
                .map(UserMapper::userToUserDto)
                .toList();

        when(userRepository.findAll())
                .thenReturn(users);

        List<UserDto> result = userService.getUsers();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(expected.get(0), result.get(0));
        assertEquals(expected.get(1), result.get(1));

        verify(userRepository).findAll();
    }

    @Test
    public void shouldThrowUserNotFoundExceptionForNonExistingUserWhenUpdatingUser() {
        when(userRepository.findById(nonExistingId))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(nonExistingId, userDto));

        verify(userRepository).findById(nonExistingId);
    }

    @Test
    public void shouldReturnUserDtoForExistingUserIdWhenUpdatingUser() {
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(userRepository.save(any(BookUser.class)))
                .thenReturn(user);

        UserDto result = userService.updateUser(userId, userDto);

        assertNotNull(result);
        assertEquals(userDto, result);

        verify(userRepository).findById(userId);
        verify(userRepository).save(any(BookUser.class));
    }

    @Test
    public void shouldThrowUserNotFoundExceptionForNonExistingUserIdWhenDeletingUser() {
        when(userRepository.existsById(nonExistingId))
                .thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(nonExistingId));

        verify(userRepository).existsById(nonExistingId);
    }

    @Test
    public void shouldDeleteUserForExistingUserIdWhenDeletingUser() {
        when(userRepository.existsById(userId))
                .thenReturn(true);

        doNothing()
                .when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository).existsById(userId);
        verify(userRepository).deleteById(userId);
    }
}
