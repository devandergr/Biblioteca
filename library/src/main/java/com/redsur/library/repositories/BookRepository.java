package com.redsur.library.repositories;

import com.redsur.library.models.Author;
import com.redsur.library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findByTitle(String title);
    List<Book> findByCategory(String category);
    List<Book> findByAuthors(Author author);
}
