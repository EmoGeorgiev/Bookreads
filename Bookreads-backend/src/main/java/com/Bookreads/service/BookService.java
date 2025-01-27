package com.Bookreads.service;

import com.Bookreads.dto.BookDto;
import com.Bookreads.exception.BookAlreadyExistsException;
import com.Bookreads.exception.BookNotFoundException;
import com.Bookreads.exception.UserNotFoundException;
import com.Bookreads.mapper.BookMapper;
import com.Bookreads.model.Book;
import com.Bookreads.model.BookUser;
import com.Bookreads.repository.BookRepository;
import com.Bookreads.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<BookDto> getBooksByUserId(Long userId) {
        return bookRepository.findByUserId(userId)
                .stream()
                .sorted(Comparator.comparing(Book::getDateRead))
                .map(BookMapper::bookToBookDto)
                .collect(Collectors.toList());
    }
    public BookDto addBook(BookDto bookDto) {
        BookUser user =  userRepository.findById(bookDto.userId())
                .orElseThrow(() -> new UserNotFoundException("There does not exist a user with such an id"));

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

        BookUser user =  userRepository.findById(bookDto.userId())
                .orElseThrow(() -> new UserNotFoundException("There does not exist a user with such an id"));

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
            throw new BookNotFoundException("There does not exist a book with such an id");
        }
        bookRepository.deleteById(id);
    }
}
