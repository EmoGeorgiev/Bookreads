package com.Bookreads.service;

import com.Bookreads.exception.UserNotFoundException;
import com.Bookreads.model.BookUser;
import com.Bookreads.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public BookUser findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("There does not exist a user with such an id"));
    }
}
