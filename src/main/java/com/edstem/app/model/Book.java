package com.edstem.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int publicationYear;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    // Constructors, getters, setters, and other fields if needed

    public Book() {
        // Default constructor
    }

    public Book(Long id, String title, int publicationYear, Author author) {
		super();
		this.id = id;
		this.title = title;
		this.publicationYear = publicationYear;
		this.author = author;
	}




	public Book(Long id, String title) {
		super();
		this.id = id;
		this.title = title;
	}




	public Book(String title, int publicationYear, Author author) {
        this.title = title;
        this.publicationYear = publicationYear;
        this.author = author;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}

