package com.Bookreads.service;

import com.Bookreads.dto.UserDto;
import com.Bookreads.exception.UserNotFoundException;
import com.Bookreads.mapper.UserMapper;
import com.Bookreads.model.BookUser;
import com.Bookreads.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getUser(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::userToUserDto)
                .orElseThrow(() -> new UserNotFoundException("There does not exist a user with such an id"));
    }

    public List<UserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::userToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        BookUser oldUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("There does not exist a user with such an id"));

        oldUser.setUsername(userDto.username());
        oldUser.setEmail(userDto.email());

        BookUser updatedUser = userRepository.save(oldUser);
        return UserMapper.userToUserDto(updatedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("There does not exist a user with such an id");
        }
        userRepository.deleteById(id);
    }
}
