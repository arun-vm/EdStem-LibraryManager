package com.edstem.app.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edstem.app.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	
    // Custom query methods as needed
    @Query("SELECT b FROM Book b WHERE b.publicationYear BETWEEN :startYear AND :endYear")
    List<Book> findBooksByPublicationYearBetween(@Param("startYear") int startYear, @Param("endYear") int endYear);
}

