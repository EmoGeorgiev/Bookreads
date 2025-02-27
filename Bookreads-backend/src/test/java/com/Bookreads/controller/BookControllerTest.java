package com.Bookreads.controller;

import com.Bookreads.dto.BookDto;
import com.Bookreads.enums.Bookshelf;
import com.Bookreads.exception.UserNotFoundException;
import com.Bookreads.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    @WithMockUser
    public void shouldReturnNotFoundForNonExistentUserId() throws Exception {
        when(bookService.getBooksByUserId(anyLong()))
                .thenThrow(new UserNotFoundException("There does not exist a user with such an id"));

        mockMvc.perform(get("/api/books/-1"))
                .andExpect(status().isNotFound());

        verify(bookService).getBooksByUserId(anyLong());
    }

    @Test
    @WithMockUser
    public void shouldReturnBooksForGivenUserId() throws Exception {
        BookDto bookDto = new BookDto(1L, "It", "Stephen King", 1000, 5, "", LocalDate.now(), Bookshelf.READ,1L);

        when(bookService.getBooksByUserId(1L))
                .thenReturn(List.of(bookDto));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(bookDto.id()))
                .andExpect(jsonPath("$[0].title").value(bookDto.title()))
                .andExpect(jsonPath("$[0].author").value(bookDto.author()))
                .andExpect(jsonPath("$[0].pageCount").value(bookDto.pageCount()))
                .andExpect(jsonPath("$[0].rating").value(bookDto.rating()))
                .andExpect(jsonPath("$[0].review").value(bookDto.review()))
                .andExpect(jsonPath("$[0].dateRead").value(bookDto.dateRead().toString()))
                .andExpect(jsonPath("$[0].bookshelf").value(bookDto.bookshelf().name()))
                .andExpect(jsonPath("$[0].userId").value(bookDto.userId()));

        verify(bookService).getBooksByUserId(1L);
    }

    @Test
    @WithMockUser
    public void shouldCreateABookAndReturnTheBookResult() throws Exception {
        BookDto bookDto = new BookDto(1L, "It", "Stephen King", 1000, 5, "", null, Bookshelf.READ,1L);

        when(bookService.addBook(bookDto))
                .thenReturn(bookDto);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookDto))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(bookDto.id()))
                .andExpect(jsonPath("$.title").value(bookDto.title()))
                .andExpect(jsonPath("$.author").value(bookDto.author()))
                .andExpect(jsonPath("$.pageCount").value(bookDto.pageCount()))
                .andExpect(jsonPath("$.rating").value(bookDto.rating()))
                .andExpect(jsonPath("$.review").value(bookDto.review()))
                .andExpect(jsonPath("$.bookshelf").value(bookDto.bookshelf().name()))
                .andExpect(jsonPath("$.userId").value(bookDto.userId()));

        verify(bookService).addBook(bookDto);
    }

    @Test
    @WithMockUser
    public void shouldReturnBadRequestWhenFieldIsInvalid() throws Exception {
        BookDto bookDto = new BookDto(1L, "", "Stephen King", 1000, 5, "", null, Bookshelf.READ,1L);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bookDto))
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Title must be between 1 and 100 characters"));
    }
}
