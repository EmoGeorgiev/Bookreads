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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.Bookreads.util.ErrorMessages.*;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public JpaUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto createUser(SignUpDto signUpDto) {
        BookUser user = UserMapper.signUpDtoToUser(signUpDto);

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException(USERNAME_ALREADY_EXISTS);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        BookUser createdUser = userRepository.save(user);
        return UserMapper.userToUserDto(createdUser);
    }

    public UserDto updatePassword(Long id, String oldPassword, String newPassword) {
        BookUser user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new PasswordsDoNotMatchException(PASSWORDS_DO_NOT_MATCH);
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        BookUser updatedUser = userRepository.save(user);
        return UserMapper.userToUserDto(updatedUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException(USERNAME_DOES_NOT_EXIST));
    }
}
