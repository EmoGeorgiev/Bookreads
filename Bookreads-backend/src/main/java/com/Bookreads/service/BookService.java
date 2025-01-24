package com.Bookreads.service;

import com.Bookreads.dto.BookDto;
import com.Bookreads.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDto> getBooksByUserId(Long id) {
        return null;
    }
    public BookDto addBook(BookDto book) {
        return null;
    }

    public BookDto updateBook(Long id, BookDto book) {
        return null;
    }

    public void removeBook(Long id) {
    }
}
