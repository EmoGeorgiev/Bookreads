package com.Bookreads.controller;

import com.Bookreads.dto.BookDto;
import com.Bookreads.enums.Bookshelf;
import com.Bookreads.exception.BookAlreadyExistsException;
import com.Bookreads.exception.BookNotFoundException;
import com.Bookreads.exception.UserNotFoundException;
import com.Bookreads.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.Bookreads.util.ErrorMessages.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BookService bookService;

    private BookDto validBookDto;
    private BookDto invalidTitleBookDto;
    private BookDto invalidUserIdBookDto;

    @BeforeEach
    public void setUp() {
        validBookDto = new BookDto(1L, "It", "Stephen King", 1000, 5, "", null, Bookshelf.READ, 1L);
        invalidTitleBookDto = new BookDto(1L, "   ", "Stephen King", 1000, 5, "", null, Bookshelf.READ, 1L);
        invalidUserIdBookDto = new BookDto(1L, "It", "Stephen King", 1000, 5, "", null, Bookshelf.READ, -1L);
    }

    @Test
    public void shouldReturnNotFoundForNonExistingUserIdWhenGettingBooks() throws Exception {
        when(bookService.getBooksByUserId(-1L))
                .thenThrow(new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

        mockMvc.perform(get("/api/books/-1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(USER_NOT_FOUND_MESSAGE));

        verify(bookService).getBooksByUserId(-1L);
    }

    @Test
    public void shouldReturnBooksForGivenUserId() throws Exception {
        when(bookService.getBooksByUserId(1L))
                .thenReturn(List.of(validBookDto));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(validBookDto.id()))
                .andExpect(jsonPath("$[0].title").value(validBookDto.title()))
                .andExpect(jsonPath("$[0].author").value(validBookDto.author()))
                .andExpect(jsonPath("$[0].pageCount").value(validBookDto.pageCount()))
                .andExpect(jsonPath("$[0].rating").value(validBookDto.rating()))
                .andExpect(jsonPath("$[0].review").value(validBookDto.review()))
                .andExpect(jsonPath("$[0].userId").value(validBookDto.userId()));

        verify(bookService).getBooksByUserId(1L);
    }

    @Test
    public void shouldCreateABookAndReturnTheBookResult() throws Exception {
        when(bookService.addBook(validBookDto))
                .thenReturn(validBookDto);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBookDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(validBookDto.id()))
                .andExpect(jsonPath("$.title").value(validBookDto.title()))
                .andExpect(jsonPath("$.author").value(validBookDto.author()))
                .andExpect(jsonPath("$.pageCount").value(validBookDto.pageCount()))
                .andExpect(jsonPath("$.rating").value(validBookDto.rating()))
                .andExpect(jsonPath("$.review").value(validBookDto.review()))
                .andExpect(jsonPath("$.bookshelf").value(validBookDto.bookshelf().name()))
                .andExpect(jsonPath("$.userId").value(validBookDto.userId()));

        verify(bookService).addBook(validBookDto);
    }

    @Test
    public void shouldReturnBadRequestWhenTitleIsInvalid() throws Exception {
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidTitleBookDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Title cannot be blank"));
    }

    @Test
    public void shouldReturnNotFoundForNonExistingUserIdWhenAddingBook() throws Exception {
        when(bookService.addBook(invalidUserIdBookDto))
                .thenThrow(new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUserIdBookDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(USER_NOT_FOUND_MESSAGE));

        verify(bookService).addBook(invalidUserIdBookDto);
    }

    @Test
    public void shouldReturnConflictForDuplicateBook() throws Exception {
        when(bookService.addBook(validBookDto))
                .thenThrow(new BookAlreadyExistsException(BOOK_ALREADY_EXISTS_MESSAGE));

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBookDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(BOOK_ALREADY_EXISTS_MESSAGE));

        verify(bookService).addBook(validBookDto);
    }

    @Test
    public void shouldUpdateBookWhenBookIdIsValidAndReturnTheBookResult() throws Exception {
        when(bookService.updateBook(1L, validBookDto))
                .thenReturn(validBookDto);

        mockMvc.perform(put("/api/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBookDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(validBookDto.id()))
                .andExpect(jsonPath("$.title").value(validBookDto.title()))
                .andExpect(jsonPath("$.author").value(validBookDto.author()))
                .andExpect(jsonPath("$.pageCount").value(validBookDto.pageCount()))
                .andExpect(jsonPath("$.rating").value(validBookDto.rating()))
                .andExpect(jsonPath("$.review").value(validBookDto.review()))
                .andExpect(jsonPath("$.bookshelf").value(validBookDto.bookshelf().name()))
                .andExpect(jsonPath("$.userId").value(validBookDto.userId()));

        verify(bookService).updateBook(1L, validBookDto);
    }

    @Test
    public void shouldReturnNotFoundForNonExistingBookIdWhenUpdatingBook() throws Exception {
        when(bookService.updateBook(-1L, validBookDto))
                .thenThrow(new BookNotFoundException(BOOK_NOT_FOUND_MESSAGE));

        mockMvc.perform(put("/api/books/{id}", -1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validBookDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(BOOK_NOT_FOUND_MESSAGE));;

        verify(bookService).updateBook(-1L, validBookDto);
    }

    @Test
    public void shouldReturnNotFoundForNonExistingUserIdWhenUpdatingBook() throws Exception {
        when(bookService.updateBook(1L, invalidUserIdBookDto))
                .thenThrow(new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

        mockMvc.perform(put("/api/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUserIdBookDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(USER_NOT_FOUND_MESSAGE));;

        verify(bookService).updateBook(1L, invalidUserIdBookDto);
    }

    @Test
    public void shouldDeleteBookWhenBookIdIsValidAndReturnNoContent() throws Exception {
        doNothing()
                .when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());

        verify(bookService).deleteBook(1L);
    }

    @Test
    public void shouldReturnNotFoundForNonExistingBookIdWhenDeletingBook() throws Exception {
        doThrow(new BookNotFoundException(BOOK_NOT_FOUND_MESSAGE))
                .when(bookService).deleteBook(-1L);

        mockMvc.perform(delete("/api/books/-1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(BOOK_NOT_FOUND_MESSAGE));

        verify(bookService).deleteBook(-1L);
    }
}
