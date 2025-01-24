package com.Bookreads.service;

import com.Bookreads.dto.BookDto;
import com.Bookreads.exception.BookAlreadyExistsException;
import com.Bookreads.exception.BookNotFoundException;
import com.Bookreads.mapper.BookMapper;
import com.Bookreads.model.Book;
import com.Bookreads.model.BookUser;
import com.Bookreads.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final UserService userService;

    public BookService(BookRepository bookRepository, UserService userService) {
        this.bookRepository = bookRepository;
        this.userService = userService;
    }

    public List<BookDto> getBooksByUserId(Long userId) {
        return bookRepository.findByUserId(userId)
                .stream()
                .sorted(Comparator.comparing(Book::getDateRead))
                .map(BookMapper::bookToBookDto)
                .collect(Collectors.toList());
    }
    public BookDto addBook(BookDto bookDto) {
        BookUser user =  userService.findUserById(bookDto.userId());

        Optional<Book> optionalBook = bookRepository.findByTitle(bookDto.title());
        if (optionalBook.isPresent()
                && optionalBook.get().getAuthor().equals(bookDto.author())) {
            throw new BookAlreadyExistsException("This book has already been added");
        }

        Book book = BookMapper.bookDtoToBook(bookDto);
        book.setUser(user);
        Book resultBook = bookRepository.save(book);

        return BookMapper.bookToBookDto(resultBook);
    }

    public BookDto updateBook(Long id, BookDto bookDto) {
        Book oldBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("There does not exist a book with such an id"));

        BookUser user =  userService.findUserById(bookDto.userId());

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

    public void removeBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("There does not exist a book with such an id");
        }
        bookRepository.deleteById(id);
    }
}
