package com.Bookreads.repository;

import com.Bookreads.model.BookUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<BookUser, Long> {
}
