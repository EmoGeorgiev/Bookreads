package com.Bookreads.mapper;

import com.Bookreads.dto.BookDto;
import com.Bookreads.model.Book;

public class BookMapper {

    public BookMapper() {

    }
    public static BookDto bookToBookDto(Book book) {
        if (book == null) {
            return null;
        }

        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPageCount(),
                book.getRating(),
                book.getReview(),
                book.getDateRead(),
                book.getBookshelf(),
                book.getUser().getId()
        );
    }

    public static Book bookDtoToBook(BookDto bookDto) {
        if (bookDto == null) {
            return null;
        }

        Book book = new Book();
        book.setId(bookDto.id());
        book.setTitle(bookDto.title());
        book.setAuthor(bookDto.author());
        book.setPageCount(bookDto.pageCount());
        book.setRating(bookDto.rating());
        book.setReview(bookDto.review());
        book.setDateRead(bookDto.dateRead());
        book.setBookshelf(bookDto.bookshelf());

        return book;
    }
}
