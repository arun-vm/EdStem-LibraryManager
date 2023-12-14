package com.edstem.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edstem.app.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    // Custom query methods as needed
	@Query("SELECT a FROM Author a WHERE SIZE(a.books) > :bookCount")
    List<Author> findAuthorsWithMoreBooksThan(@Param("bookCount") int bookCount);
}

