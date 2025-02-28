package com.Bookreads.model;

import com.Bookreads.enums.Bookshelf;
import com.Bookreads.exception.PageCountCannotBeLessThanOneException;
import com.Bookreads.exception.RatingMustBeBetweenOneAndFiveException;
import jakarta.persistence.*;

import java.time.LocalDate;

import static com.Bookreads.util.ErrorMessages.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private Integer pageCount;
    @Column(nullable = true)
    private Integer rating;
    @Column(nullable = true, length = 5000)
    private String review;
    @Column(nullable = true)
    private LocalDate dateRead;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Bookshelf bookshelf;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private BookUser user;

    public Book() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        if (pageCount <= 0) {
            throw new PageCountCannotBeLessThanOneException(PAGE_COUNT_CANNOT_BE_LESS_THAN_ONE);
        }
        this.pageCount = pageCount;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        if (rating == null) {
            return;
        }
        if (rating <= 0 || rating > 5) {
            throw new RatingMustBeBetweenOneAndFiveException(RATING_MUST_BE_BETWEEN_ONE_AND_FIVE);
        }
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public LocalDate getDateRead() {
        return dateRead;
    }

    public void setDateRead(LocalDate dateRead) {
        this.dateRead = dateRead;
    }

    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    public void setBookshelf(Bookshelf bookshelf) {
        this.bookshelf = bookshelf;
    }

    public BookUser getUser() {
        return user;
    }

    public void setUser(BookUser user) {
        this.user = user;
    }
}
