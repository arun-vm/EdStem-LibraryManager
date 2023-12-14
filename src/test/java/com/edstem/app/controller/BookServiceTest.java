package com.edstem.app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.edstem.app.model.Author;
import com.edstem.app.model.Book;
import com.edstem.app.repository.BookRepository;
import com.edstem.app.service.BookService;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testGetAllBooks() {
        // Mock data
        List<Book> books = Arrays.asList(
                new Book(1L, "Book 1", 2000, new Author("Author 1")),
                new Book(2L, "Book 2", 2005, new Author("Author 2"))
        );

        // Mock behavior
        when(bookRepository.findAll()).thenReturn(books);

        // Test
        List<Book> result = bookService.getAllBooks();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetBookById() {
        // Mock data
        Book book = new Book(1L, "Test Book", 2000, new Author("Test Author"));

        // Mock behavior
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Test
        Optional<Book> result = bookService.getBookById(1L);
        assertTrue(result.isPresent());
        assertEquals("Test Book", result.get().getTitle());
    }

    @Test
    public void testGetBookById_NotFound() {
        // Mock behavior
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Test
        Optional<Book> result = bookService.getBookById(1L);
        assertFalse(result.isPresent());
    }

    @Test
    public void testCreateOrUpdateBook_CreateNewBook() {
        // Mock data
        Book newBook = new Book("New Book", 2022, new Author("New Author"));

        // Mock behavior
        when(bookRepository.save(any(Book.class))).thenReturn(new Book(1L, "New Book", 2022, new Author("New Author")));

        // Test
        Book result = bookService.createOrUpdateBook(newBook);
        assertNotNull(result);
        assertEquals("New Book", result.getTitle());
    }

    @Test
    public void testCreateOrUpdateBook_UpdateExistingBook() {
        // Mock data
        Book existingBook = new Book(1L, "Existing Book", 2000, new Author("Existing Author"));

        // Mock behavior
        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(existingBook)).thenReturn(existingBook);

        // Test
        Book result = bookService.createOrUpdateBook(existingBook);
        assertNotNull(result);
        assertEquals("Existing Book", result.getTitle());
    }

    @Test
    public void testDeleteBookById() {
        // Mock behavior
        doNothing().when(bookRepository).deleteById(1L);

        // Test
        bookService.deleteBookById(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }
}
