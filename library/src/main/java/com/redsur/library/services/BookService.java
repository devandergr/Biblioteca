package com.redsur.library.services;

import com.redsur.library.models.Author;
import com.redsur.library.models.Book;
import com.redsur.library.repositories.AuthorRepository;
import com.redsur.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        if (book.getAuthors() != null) {
            Set<Author> authors = new HashSet<>();
            for (Author author : book.getAuthors()) {
                Author existingAuthor = authorRepository.findByFirstNameAndLastNameAndNationality(
                        author.getFirst_name(), author.getLast_name(), author.getNationality()).orElse(null);
                if (existingAuthor != null) {
                    authors.add(existingAuthor);
                } else {
                    Author newAuthor = new Author();
                    newAuthor.setFirst_name(author.getFirst_name());
                    newAuthor.setLast_name(author.getLast_name());
                    newAuthor.setNationality(author.getNationality());
                    Author savedAuthor = authorRepository.save(newAuthor);
                    authors.add(savedAuthor);
                }
            }
            book.setAuthors(authors);
        }

        if (book.getAvailable() == null) {
            book.setAvailable(0); // Por defecto, si no se proporciona, se establece en 0
        }

        return bookRepository.save(book);
    }

    public Book getBookById(Integer id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public List<Book> getBooksByCategory(String category) {
        return bookRepository.findByCategory(category);
    }

    public List<Book> getBooksByAuthors(Integer authorId) {
        Author author = authorRepository.findById(authorId).orElse(null);
        if (author != null) {
            return bookRepository.findByAuthors(author);
        }
        return null;
    }

    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }

    public Book updateBookTitle (Integer id, Book updatedBook) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            book.setTitle(updatedBook.getTitle());
            return bookRepository.save(book);
        }
        return null;
    }
}
