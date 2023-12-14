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
import com.edstem.app.repository.AuthorRepository;
import com.edstem.app.service.AuthorService;

@RunWith(MockitoJUnitRunner.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    public void testGetAllAuthors() {
        // Mock data
        List<Author> authors = Arrays.asList(new Author(1L, "Author 1"), new Author(2L, "Author 2"));

        // Mock behavior
        when(authorRepository.findAll()).thenReturn(authors);

        // Test
        List<Author> result = authorService.getAllAuthors();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetAuthorById() {
        // Mock data
        Author author = new Author(1L, "Test Author");

        // Mock behavior
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        // Test
        Optional<Author> result = authorService.getAuthorById(1L);
        assertTrue(result.isPresent());
        assertEquals("Test Author", result.get().getName());
    }

    @Test
    public void testGetAuthorById_NotFound() {
        // Mock behavior
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        // Test
        Optional<Author> result = authorService.getAuthorById(1L);
        assertFalse(result.isPresent());
    }

    @Test
    public void testCreateOrUpdateAuthor_CreateNewAuthor() {
        // Mock data
        Author newAuthor = new Author("New Author");

        // Mock behavior
        when(authorRepository.save(any(Author.class))).thenReturn(new Author(1L, "New Author"));

        // Test
        Author result = authorService.createOrUpdateAuthor(newAuthor);
        assertNotNull(result);
        assertEquals("New Author", result.getName());
    }

    @Test
    public void testCreateOrUpdateAuthor_UpdateExistingAuthor() {
        // Mock data
        Author existingAuthor = new Author(1L, "Existing Author");

        // Mock behavior
        when(authorRepository.findById(1L)).thenReturn(Optional.of(existingAuthor));
        when(authorRepository.save(existingAuthor)).thenReturn(existingAuthor);

        // Test
        Author result = authorService.createOrUpdateAuthor(existingAuthor);
        assertNotNull(result);
        assertEquals("Existing Author", result.getName());
    }

    @Test
    public void testDeleteAuthorById() {
        // Mock behavior
        doNothing().when(authorRepository).deleteById(1L);

        // Test
        authorService.deleteAuthorById(1L);
        verify(authorRepository, times(1)).deleteById(1L);
    }
}
