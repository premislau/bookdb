package com.bookdb.controllers;

import com.bookdb.entities.Author;
import com.bookdb.entities.Book;
import com.bookdb.services.BookService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Validated
public class BookController {
    private final BookService bookService;

    @GetMapping("/")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok().body(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getEmployeeById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(bookService.getBookById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        return ResponseEntity.ok().body(bookService.saveBook(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable UUID id) {
        bookService.deleteBookById(id);
        return ResponseEntity.ok().body("Deletion completed successfully");
    }

    @GetMapping("/{id}/authors/")
    public ResponseEntity<List<Author>> getBookAuthors(@PathVariable UUID id) {
        return ResponseEntity.ok().body(bookService.getBookById(id).getAuthors());
    }

    @PostMapping("/{bookId}/authors/")
    public ResponseEntity<?> addMultipleAuthors(@PathVariable UUID bookId, @RequestBody List<UUID> authorIds) {
        try {
            return ResponseEntity.ok().body(bookService.addMultipleAuthorsToBook(bookId, authorIds));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/{bookId}/authors/{authorId}")
    public ResponseEntity<Book> removeBookAuthor(@PathVariable UUID authorId, @PathVariable UUID bookId) {
        return ResponseEntity.ok().body(bookService.removeBookAuthor(bookId, authorId));
    }
}
