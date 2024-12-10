package com.bookdb.services;

import com.bookdb.entities.Author;
import com.bookdb.entities.Book;
import com.bookdb.repositories.AuthorRepository;
import com.bookdb.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(UUID id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        return optionalAuthor.orElse(null);
    }

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public void deleteAuthorById(UUID id) {
        authorRepository.deleteById(id);
    }

    public Author addBookToAuthor(UUID authorId, UUID bookId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        author.addBook(book);
        return authorRepository.save(author);
    }

    public Author addMultipleBooksToAuthor(UUID authorId, List<UUID> bookIds) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
        List<Book> books = new ArrayList<>(bookIds.size());
        for(UUID bookId:bookIds) {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new EntityNotFoundException("Book not found"));
            books.add(book);
        }
        //books are added to the author only if all bookIds are in the database
        for(Book book:books) {
            author.addBook(book);
        }
        return authorRepository.save(author);
    }

    public Author removeAuthorBook(UUID authorId, UUID bookId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        author.removeBook(book);
        return authorRepository.save(author);
    }
}
