package com.Bookreads.service;

import com.Bookreads.dto.UserDto;
import com.Bookreads.exception.UserNotFoundException;
import com.Bookreads.mapper.UserMapper;
import com.Bookreads.model.BookUser;
import com.Bookreads.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.Bookreads.util.ErrorMessages.USER_NOT_FOUND_MESSAGE;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    public BookUser getUserEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));
    }

    public UserDto getUser(Long id) {
        return UserMapper.userToUserDto(getUserEntity(id));
    }

    public List<UserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        BookUser oldUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

        oldUser.setUsername(userDto.username());
        oldUser.setEmail(userDto.email());

        BookUser updatedUser = userRepository.save(oldUser);
        return UserMapper.userToUserDto(updatedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(USER_NOT_FOUND_MESSAGE);
        }
        userRepository.deleteById(id);
    }
}
