package com.edstem.app.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edstem.app.model.Author;
import com.edstem.app.repository.AuthorRepository;

/**
 * Service class to manage operations related to Authors.
 */
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * Retrieves all authors from the database.
     *
     * @return List of all authors
     */
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    /**
     * Retrieves an author by ID from the database.
     *
     * @param authorId ID of the author
     * @return Optional containing the author if found, else empty
     */
    public Optional<Author> getAuthorById(Long authorId) {
        return authorRepository.findById(authorId);
    }

    /**
     * Creates or updates an author in the database.
     *
     * @param author Author object to be created or updated
     * @return Created or updated author object
     */
    public Author createOrUpdateAuthor(Author author) {
        return authorRepository.save(author);
    }

    /**
     * Deletes an author by ID from the database.
     *
     * @param authorId ID of the author to be deleted
     */
    public void deleteAuthorById(Long authorId) {
        authorRepository.deleteById(authorId);
    }

    // Other business logic or methods related to authors if needed
}


