package com.Bookreads.controller;

import com.Bookreads.dto.BookDto;
import com.Bookreads.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookDto> addBook(@RequestBody @Valid BookDto book)  {
        BookDto resultBook = bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(resultBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody @Valid BookDto book) {
        BookDto updatedBook = bookService.updateBook(id, book);
        return ResponseEntity.ok()
                .body(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.removeBook(id);
        return ResponseEntity.noContent()
                .build();
    }
}
