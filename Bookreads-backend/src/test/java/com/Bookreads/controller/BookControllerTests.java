package com.Bookreads.controller;

import com.Bookreads.dto.BookDto;
import com.Bookreads.enums.Bookshelf;
import com.Bookreads.exception.UserNotFoundException;
import com.Bookreads.service.BookService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTests {

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
        BookDto book = new BookDto(1L, "It", "Stephen King", 1000, 5, "", LocalDate.now(), Bookshelf.READ,1L);

        when(bookService.getBooksByUserId(1L))
                .thenReturn(List.of(book));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(book.id()))
                .andExpect(jsonPath("$[0].title").value(book.title()))
                .andExpect(jsonPath("$[0].author").value(book.author()))
                .andExpect(jsonPath("$[0].pageCount").value(book.pageCount()))
                .andExpect(jsonPath("$[0].rating").value(book.rating()))
                .andExpect(jsonPath("$[0].review").value(book.review()))
                .andExpect(jsonPath("$[0].dateRead").value(book.dateRead().toString()))
                .andExpect(jsonPath("$[0].bookshelf").value(Bookshelf.READ.name()))
                .andExpect(jsonPath("$[0].userId").value(book.userId()));

        verify(bookService).getBooksByUserId(1L);
    }


}
