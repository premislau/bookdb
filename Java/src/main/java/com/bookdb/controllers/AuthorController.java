package com.bookdb.controllers;

import com.bookdb.entities.Author;
import com.bookdb.entities.Book;
import com.bookdb.services.AuthorService;
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
@RequestMapping("/authors")
@RequiredArgsConstructor
@Validated
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/")
    public ResponseEntity<List<Author>> getAllAuthors() {
        return ResponseEntity.ok().body(authorService.getAllAuthors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getEmployeeById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(authorService.getAuthorById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Author> saveAuthor(@RequestBody Author author) {
        return ResponseEntity.ok().body(authorService.saveAuthor(author));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthorById(@PathVariable UUID id) {
        authorService.deleteAuthorById(id);
        return ResponseEntity.ok().body("Deletion completed successfully");
    }

    @GetMapping("/{id}/books/")
    public ResponseEntity<List<Book>> getAuthorBooks(@PathVariable UUID id) {
        return ResponseEntity.ok().body(authorService.getAuthorById(id).getBooks());
    }

    @PostMapping("/{authorId}/books/")
    public ResponseEntity<?> addMultipleBooks(@PathVariable UUID authorId, @RequestBody List<UUID> bookIds) {
        try {
            return ResponseEntity.ok().body(authorService.addMultipleBooksToAuthor(authorId, bookIds));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    @PostMapping("/{authorId}/books/{bookId}")
    public ResponseEntity<Author> removeBook(@PathVariable UUID authorId, @PathVariable UUID bookId) {
        return ResponseEntity.ok().body(authorService.removeAuthorBook(authorId, bookId));
    }
}