package com.redsur.library.controllers;

import com.redsur.library.models.Book;
import com.redsur.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/title")
    public ResponseEntity<Book> getBookByTitle(@RequestParam("title") String title){
        Book book = bookService.getBookByTitle(title);
        if (book != null ) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/category")
    public ResponseEntity<List<Book>> getBooksByCategory(@RequestParam("category") String category){
        List<Book> books = bookService.getBooksByCategory(category);
        if (!books.isEmpty()) {
            return ResponseEntity.ok(books);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/author_id")
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam("author_id") Integer authorId){
        List<Book> books = bookService.getBooksByAuthors(authorId);
        if (!books.isEmpty()) {
            return ResponseEntity.ok(books);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        Book savedBook = bookService.saveBook(book);
        return ResponseEntity.ok(savedBook);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Integer id) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            throw new ResourceNotFoundException("Book Not Found");
        }
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
        @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> updateBookTitle(@PathVariable("id") Integer id, @RequestBody Book updatedBook){
        Book book = bookService.updateBookTitle(id, updatedBook);
        if(book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
