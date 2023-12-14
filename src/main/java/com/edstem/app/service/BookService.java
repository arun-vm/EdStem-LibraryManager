package com.edstem.app.service;

import com.edstem.app.model.Book;
import com.edstem.app.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


/**
 * Service class to manage operations related to Books.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieves all books from the database.
     *
     * @return List of all books
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Retrieves a book by ID from the database.
     *
     * @param bookId ID of the book
     * @return Optional containing the book if found, else empty
     */
    public Optional<Book> getBookById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    /**
     * Creates or updates a book in the database.
     *
     * @param book Book object to be created or updated
     * @return Created or updated book object
     */
    public Book createOrUpdateBook(Book book) {
        return bookRepository.save(book);
    }

    /**
     * Deletes a book by ID from the database.
     *
     * @param bookId ID of the book to be deleted
     */
    public void deleteBookById(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    // Other business logic or methods related to books if needed
}


