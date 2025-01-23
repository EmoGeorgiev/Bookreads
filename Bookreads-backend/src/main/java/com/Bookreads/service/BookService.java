package com.Bookreads.service;

import com.Bookreads.dto.BookDto;
import com.Bookreads.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
