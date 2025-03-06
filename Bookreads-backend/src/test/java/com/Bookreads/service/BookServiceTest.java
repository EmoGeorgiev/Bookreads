package com.Bookreads.service;

import com.Bookreads.dto.BookDto;
import com.Bookreads.enums.Bookshelf;
import com.Bookreads.exception.BookAlreadyExistsException;
import com.Bookreads.exception.BookNotFoundException;
import com.Bookreads.exception.UserNotFoundException;
import com.Bookreads.mapper.BookMapper;
import com.Bookreads.model.Book;
import com.Bookreads.model.BookUser;
import com.Bookreads.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private BookService bookService;

    private BookUser user;
    private Book book;
    private Book secondBook;
    private BookDto bookDto;
    private Long nonExistingId;
    private Long userId;

    @BeforeEach
    void setUp() {
        nonExistingId = -1L;
        userId = 1L;

        user = new BookUser();
        user.setId(userId);
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("username@gmail.com");
        user.setBooks(new ArrayList<>());

        book = new Book();
        book.setId(1L);
        book.setTitle("title");
        book.setAuthor("author");
        book.setPageCount(100);
        book.setRating(4);
        book.setReview("Review of title by author");
        book.setDateRead(LocalDate.now());
        book.setBookshelf(Bookshelf.READ);
        book.setUser(user);

        secondBook = new Book();
        secondBook.setId(2L);
        secondBook.setTitle(book.getTitle());
        secondBook.setAuthor("other author");
        secondBook.setPageCount(200);
        secondBook.setRating(2);
        secondBook.setReview("review");
        secondBook.setBookshelf(Bookshelf.READ);
        secondBook.setDateRead(null);
        secondBook.setUser(user);

        user.getBooks().add(book);
        user.getBooks().add(secondBook);

        bookDto = BookMapper.bookToBookDto(book);
    }

    @Test
    public void shouldThrowUserNotFoundExceptionForNonExistingUserIdWhenGettingBooksByUserId() {
        when(userService.existsById(nonExistingId))
                .thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> bookService.getBooksByUserId(nonExistingId));

        verify(userService).existsById(nonExistingId);
    }

    @Test
    public void shouldReturnEmptyListOfBookDtoForExistingUserWithNoBooksWhenGettingBooksByUserId() {
        when(userService.existsById(userId))
                .thenReturn(true);

        when(bookRepository.findByUserId(userId))
                .thenReturn(new ArrayList<>());

        List<BookDto> result = bookService.getBooksByUserId(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(userService).existsById(userId);
        verify(bookRepository).findByUserId(userId);
    }

    @Test
    public void shouldReturnListOfBookDtoForExistingUserWithTwoBooksWhenGettingBooksByUserId() {
        List<BookDto> expectedBooks = Stream.of(book, secondBook)
                        .map(BookMapper::bookToBookDto)
                        .toList();

        when(userService.existsById(userId))
                .thenReturn(true);

        when(bookRepository.findByUserId(userId))
                .thenReturn(user.getBooks());

        List<BookDto> result = bookService.getBooksByUserId(userId);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(expectedBooks.size(), result.size());
        assertEquals(expectedBooks.get(1), result.get(0));
        assertEquals(expectedBooks.get(0), result.get(1));

        verify(userService).existsById(userId);
        verify(bookRepository).findByUserId(userId);
    }

    @Test
    public void shouldThrowBookAlreadyExistsExceptionForMatchingTitleAndAuthorWhenAddingBook() {
        String title = book.getTitle();

        Optional<Book> optionalBook = Optional.of(book);

        when(bookRepository.findByTitleAndUserId(title, userId))
                .thenReturn(optionalBook);

        assertThrows(BookAlreadyExistsException.class, () -> bookService.addBook(bookDto));

        verify(bookRepository).findByTitleAndUserId(title, userId);
    }

    @Test
    public void shouldReturnBookDtoForNonExistingBookWhenAddingBook() {
        String title = book.getTitle();

        when(userService.getUserEntity(userId))
                .thenReturn(user);

        when(bookRepository.findByTitleAndUserId(title, userId))
                .thenReturn(Optional.empty());

        when(bookRepository.save(any(Book.class)))
                .thenReturn(book);

        BookDto result = bookService.addBook(bookDto);

        assertNotNull(result);
        assertEquals(result, bookDto);

        verify(userService).getUserEntity(userId);
        verify(bookRepository).findByTitleAndUserId(title, userId);
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    public void shouldReturnBookDtoForBookWithSameTitleButDifferentAuthorWhenAddingBook() {
        String title = book.getTitle();

        when(userService.getUserEntity(userId))
                .thenReturn(user);

        when(bookRepository.findByTitleAndUserId(title, userId))
                .thenReturn(Optional.of(secondBook));

        when(bookRepository.save(any(Book.class)))
                .thenReturn(book);

        BookDto result = bookService.addBook(bookDto);

        assertNotNull(result);
        assertEquals(result, bookDto);

        verify(userService).getUserEntity(userId);
        verify(bookRepository).findByTitleAndUserId(title,userId);
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    public void shouldThrowBookNotFoundExceptionForNonExistingIdWhenUpdatingBook() {
        when(bookRepository.findById(nonExistingId))
                .thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(nonExistingId, any(BookDto.class)));

        verify(bookRepository).findById(nonExistingId);
    }

    @Test
    public void shouldReturnBookDtoForExistingBookIdWhenUpdatingBook() {
        Long bookId = book.getId();

        when(bookRepository.findById(bookId))
                .thenReturn(Optional.of(book));

        when(userService.getUserEntity(userId))
                .thenReturn(user);

        when(bookRepository.save(any(Book.class)))
                .thenReturn(book);

        BookDto result = bookService.updateBook(bookId, bookDto);

        assertNotNull(result);
        assertEquals(result, bookDto);

        verify(bookRepository).findById(bookId);
        verify(userService).getUserEntity(userId);
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    public void shouldThrowBookNotFoundExceptionForNonExistingBookIdWhenDeletingBook() {
        when(bookRepository.existsById(nonExistingId))
                .thenReturn(false);

        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(nonExistingId));

        verify(bookRepository).existsById(nonExistingId);
    }

    @Test
    public void shouldDeleteBookForExistingBookIdWhenDeletingBook() {
        Long bookId = book.getId();

        when(bookRepository.existsById(bookId))
                .thenReturn(true);

        doNothing()
                .when(bookRepository).deleteById(bookId);

        bookService.deleteBook(user.getId());

        verify(bookRepository).existsById(bookId);
        verify(bookRepository).deleteById(bookId);
    }
}
