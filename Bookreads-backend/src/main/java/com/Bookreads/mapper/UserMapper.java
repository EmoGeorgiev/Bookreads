package com.Bookreads.mapper;

import com.Bookreads.dto.BookDto;
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
}
