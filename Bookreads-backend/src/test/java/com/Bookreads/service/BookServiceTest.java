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

    @BeforeEach
    void setUp() {
        nonExistingId = -1L;

        user = new BookUser();
        user.setId(1L);
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

        bookDto = new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getPageCount(), book.getRating(), book.getReview(), book.getDateRead(), book.getBookshelf(), book.getUser().getId());
    }

    @Test
    public void shouldThrowUserNotFoundExceptionForNonExistingUserIdWhenGettingBooksByUserId() {
        when(userService.existsById(nonExistingId))
                .thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> bookService.getBooksByUserId(nonExistingId));

        verify(userService).existsById(nonExistingId);
    }

    @Test
    public void shouldReturnListOfBookDtoForExistingUserWithNoBooksWhenGettingBooksByUserId() {
        when(userService.existsById(user.getId()))
                .thenReturn(true);

        when(bookRepository.findByUserId(user.getId()))
                .thenReturn(new ArrayList<>());

        List<BookDto> result = bookService.getBooksByUserId(user.getId());

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(userService).existsById(user.getId());
        verify(bookRepository).findByUserId(user.getId());
    }

    @Test
    public void shouldReturnListOfBookDtoForExistingUserWithTwoBooksWhenGettingBooksByUserId() {
        List<BookDto> expectedBooks = Stream.of(book, secondBook)
                        .map(BookMapper::bookToBookDto)
                        .toList();

        when(userService.existsById(user.getId()))
                .thenReturn(true);

        when(bookRepository.findByUserId(user.getId()))
                .thenReturn(user.getBooks());

        List<BookDto> result = bookService.getBooksByUserId(user.getId());

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(expectedBooks.size(), result.size());
        assertEquals(expectedBooks.get(1), result.get(0));
        assertEquals(expectedBooks.get(0), result.get(1));

        verify(userService).existsById(user.getId());
        verify(bookRepository).findByUserId(user.getId());
    }

    @Test
    public void shouldThrowBookAlreadyExistsExceptionForMatchingTitleAndAuthorWhenAddingBook() {
        Optional<Book> optionalBook = Optional.of(book);

        when(bookRepository.findByTitleAndUserId(book.getTitle(), book.getUser().getId()))
                .thenReturn(optionalBook);

        assertThrows(BookAlreadyExistsException.class, () -> bookService.addBook(bookDto));

        verify(bookRepository).findByTitleAndUserId(book.getTitle(), book.getUser().getId());
    }

    @Test
    public void shouldReturnBookDtoForNonExistingBookWhenAddingBook() {
        when(userService.getUserEntity(user.getId()))
                .thenReturn(user);

        when(bookRepository.findByTitleAndUserId(book.getTitle(), book.getUser().getId()))
                .thenReturn(Optional.empty());

        when(bookRepository.save(any(Book.class)))
                .thenReturn(book);

        BookDto result = bookService.addBook(bookDto);

        assertNotNull(result);
        assertEquals(result, bookDto);

        verify(userService).getUserEntity(user.getId());
        verify(bookRepository).findByTitleAndUserId(book.getTitle(), book.getUser().getId());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    public void shouldReturnBookDtoForBookWithSameTitleButDifferentAuthorWhenAddingBook() {
        when(userService.getUserEntity(user.getId()))
                .thenReturn(user);

        when(bookRepository.findByTitleAndUserId(book.getTitle(), book.getUser().getId()))
                .thenReturn(Optional.of(secondBook));

        when(bookRepository.save(any(Book.class)))
                .thenReturn(book);

        BookDto result = bookService.addBook(bookDto);

        assertNotNull(result);
        assertEquals(result, bookDto);

        verify(userService).getUserEntity(user.getId());
        verify(bookRepository).findByTitleAndUserId(book.getTitle(), book.getUser().getId());
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
        when(bookRepository.findById(book.getId()))
                .thenReturn(Optional.of(book));

        when(userService.getUserEntity(user.getId()))
                .thenReturn(user);

        when(bookRepository.save(any(Book.class)))
                .thenReturn(book);

        BookDto result = bookService.updateBook(book.getId(), bookDto);

        assertNotNull(result);
        assertEquals(result, bookDto);

        verify(bookRepository).findById(book.getId());
        verify(userService).getUserEntity(user.getId());
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
        when(bookRepository.existsById(user.getId()))
                .thenReturn(true);

        doNothing()
                .when(bookRepository).deleteById(user.getId());

        bookService.deleteBook(user.getId());

        verify(bookRepository).existsById(user.getId());
        verify(bookRepository).deleteById(user.getId());
    }
}
