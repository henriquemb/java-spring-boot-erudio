package com.github.henriquemb.repository;

import com.github.henriquemb.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
