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
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(UUID id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.orElse(null);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBookById(UUID id) {
        bookRepository.deleteById(id);
    }

    public Book addAuthorToBook(UUID bookId, UUID authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        book.addAuthor(author);
        return bookRepository.save(book);
    }

    public Book addMultipleAuthorsToBook(UUID bookId, List<UUID> authorIds) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        List<Author> authors=new ArrayList<>(authorIds.size());
        for(UUID authorId:authorIds) {
            Author author = authorRepository.findById(authorId)
                    .orElseThrow(() -> new EntityNotFoundException("Author not found"));
            authors.add(author);
        }
        //authors are added to the book only if all authorIds are in the database
        for(Author author:authors) {
            book.addAuthor(author);
        }
        return bookRepository.save(book);
    }

    public Book removeBookAuthor(UUID bookId, UUID authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        book.removeAuthor(author);
        return bookRepository.save(book);
    }
}
