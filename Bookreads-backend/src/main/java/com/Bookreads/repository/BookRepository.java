package com.Bookreads.repository;

import com.Bookreads.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitleAndUserId(String title, Long userId);
    @Query("SELECT b FROM Book b WHERE b.user.id = :id")
    List<Book> findByUserId(Long id);
}
