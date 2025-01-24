package com.Bookreads.mapper;

import com.Bookreads.dto.BookDto;
import com.Bookreads.dto.SignUpDto;
import com.Bookreads.dto.UserDto;
import com.Bookreads.model.BookUser;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public UserMapper() {

    }

    public static UserDto userToUserDto(BookUser user) {
        if (user == null) {
            return null;
        }

        List<BookDto> booksDto = user
                .getBooks()
                .stream()
                .map(BookMapper::bookToBookDto)
                .collect(Collectors.toList());

        return new UserDto(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
                booksDto
        );
    }

    public static BookUser signUpDtoToUser(SignUpDto signUpDto) {
        if (signUpDto == null) {
            return null;
        }
        BookUser user = new BookUser();
        user.setUsername(signUpDto.username());
        user.setPassword(signUpDto.password());
        user.setEmail(signUpDto.email());
        return user;
    }
}
