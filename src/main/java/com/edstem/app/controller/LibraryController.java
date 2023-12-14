package com.edstem.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edstem.app.model.Author;
import com.edstem.app.model.Book;
import com.edstem.app.service.AuthorService;
import com.edstem.app.service.BookService;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

	private final AuthorService authorService;
	private final BookService bookService;

	@Autowired
	public LibraryController(AuthorService authorService, BookService bookService) {
		this.authorService = authorService;
		this.bookService = bookService;
	}

	// Get a list of all books with their respective author details
	@GetMapping("/books")
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> books = bookService.getAllBooks();
		return ResponseEntity.ok(books);
	}

	// Get details of a specific book by ID including its author information
	@GetMapping("/books/{bookId}")
	public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
		Optional<Book> book = bookService.getBookById(bookId);
		return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	// Add a new book with an existing author or create a new author if not existing
	@PostMapping("/books")
	public ResponseEntity<Book> addBook(@RequestBody Book book) {
		// Check if author exists, if not, create a new author
		Author author = book.getAuthor();
		if (author != null && author.getId() == null) {
			author = authorService.createOrUpdateAuthor(author);
			book.setAuthor(author);
		}

		Book createdBook = bookService.createOrUpdateBook(book);
		return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
	}

	// Update the details of an existing book or author
	@PutMapping("/books/{bookId}")
	public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody Book book) {
		Optional<Book> existingBookOptional = bookService.getBookById(bookId);
		if (existingBookOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Book existingBook = existingBookOptional.get();
		Author author = book.getAuthor();
		if (author != null && author.getId() == null) {
			author = authorService.createOrUpdateAuthor(author);
			existingBook.setAuthor(author);
		}

		existingBook.setTitle(book.getTitle());
		existingBook.setPublicationYear(book.getPublicationYear());

		Book updatedBook = bookService.createOrUpdateBook(existingBook);
		return ResponseEntity.ok(updatedBook);
	}

	// Delete a book by ID
	@DeleteMapping("/books/{bookId}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
		Optional<Book> book = bookService.getBookById(bookId);
		if (book.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		bookService.deleteBookById(bookId);
		return ResponseEntity.noContent().build();
	}

	// Get a list of all authors
	@GetMapping("/authors")
	public ResponseEntity<List<Author>> getAllAuthors() {
		List<Author> authors = authorService.getAllAuthors();
		return ResponseEntity.ok(authors);
	}

	// Get details of a specific author by ID
	@GetMapping("/authors/{authorId}")
	public ResponseEntity<Author> getAuthorById(@PathVariable Long authorId) {
		Optional<Author> author = authorService.getAuthorById(authorId);
		return author.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	// Add a new author
	@PostMapping("/authors")
	public ResponseEntity<Author> addAuthor(@RequestBody Author author) {
		Author createdAuthor = authorService.createOrUpdateAuthor(author);
		return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
	}

	// Update the details of an existing author
	@PutMapping("/authors/{authorId}")
	public ResponseEntity<Author> updateAuthor(@PathVariable Long authorId, @RequestBody Author author) {
		Optional<Author> existingAuthorOptional = authorService.getAuthorById(authorId);
		if (existingAuthorOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Author existingAuthor = existingAuthorOptional.get();
		existingAuthor.setName(author.getName()); // Update other properties as needed

		Author updatedAuthor = authorService.createOrUpdateAuthor(existingAuthor);
		return ResponseEntity.ok(updatedAuthor);
	}

	// Delete an author by ID
	@DeleteMapping("/authors/{authorId}")
	public ResponseEntity<Void> deleteAuthor(@PathVariable Long authorId) {
		Optional<Author> author = authorService.getAuthorById(authorId);
		if (author.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		authorService.deleteAuthorById(authorId);
		return ResponseEntity.noContent().build();
	}
	
//    // Endpoint to get books published within a specific range of years
//    @GetMapping("/books/published-between")
//    public ResponseEntity<List<Book>> getBooksPublishedBetween(@RequestParam("startYear") int startYear, @RequestParam("endYear") int endYear) {
//        List<Book> books = bookRepository.findBooksByPublicationYearBetween(startYear, endYear);
//        return ResponseEntity.ok(books);
//    }
//
//    // Endpoint to get authors with more than a certain number of books
//    @GetMapping("/authors/more-than-books")
//    public ResponseEntity<List<Author>> getAuthorsWithMoreBooksThan(@RequestParam("bookCount") int bookCount) {
//        List<Author> authors = authorService.findAuthorsWithMoreBooksThan(bookCount);
//        return ResponseEntity.ok(authors);
//    }
}
