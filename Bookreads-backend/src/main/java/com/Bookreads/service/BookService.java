package com.Bookreads.service;

import com.Bookreads.dto.BookDto;
import com.Bookreads.exception.BookAlreadyExistsException;
import com.Bookreads.exception.BookNotFoundException;
import com.Bookreads.exception.UserNotFoundException;
import com.Bookreads.mapper.BookMapper;
import com.Bookreads.model.Book;
import com.Bookreads.model.BookUser;
import com.Bookreads.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.Bookreads.util.ErrorMessages.*;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final UserService userService;

    public BookService(BookRepository bookRepository, UserService userService) {
        this.bookRepository = bookRepository;
        this.userService = userService;
    }

    public List<BookDto> getBooksByUserId(Long userId) {
        if (!userService.existsById(userId)) {
            throw new UserNotFoundException(USER_NOT_FOUND_MESSAGE);
        }

        return bookRepository.findByUserId(userId)
                .stream()
                .sorted(Comparator.comparing(Book::getDateRead, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .map(BookMapper::bookToBookDto)
                .collect(Collectors.toList());
    }
    public BookDto addBook(BookDto bookDto) {
        BookUser user =  userService.getUserEntity(bookDto.userId());

        Optional<Book> optionalBook = bookRepository.findByTitleAndUserId(bookDto.title(), bookDto.userId());
        if (optionalBook.isPresent()
                && optionalBook.get().getAuthor().equals(bookDto.author())) {
            throw new BookAlreadyExistsException(BOOK_ALREADY_EXISTS_MESSAGE);
        }

        Book book = BookMapper.bookDtoToBook(bookDto);
        book.setUser(user);
        Book resultBook = bookRepository.save(book);

        return BookMapper.bookToBookDto(resultBook);
    }

    public BookDto updateBook(Long id, BookDto bookDto) {
        Book oldBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(BOOK_NOT_FOUND_MESSAGE));

        BookUser user =  userService.getUserEntity(bookDto.userId());

        oldBook.setTitle(bookDto.title());
        oldBook.setAuthor(bookDto.author());
        oldBook.setPageCount(bookDto.pageCount());
        oldBook.setRating(bookDto.rating());
        oldBook.setReview(bookDto.review());
        oldBook.setDateRead(bookDto.dateRead());
        oldBook.setBookshelf(bookDto.bookshelf());
        oldBook.setUser(user);
        Book updatedBook = bookRepository.save(oldBook);

        return BookMapper.bookToBookDto(updatedBook);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(BOOK_NOT_FOUND_MESSAGE);
        }
        bookRepository.deleteById(id);
    }
}
