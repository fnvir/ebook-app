package com.app.ebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.ebook.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	
	@Query("SELECT b FROM Book b JOIN FETCH b.uploader")
	List<Book> findAll();
	
}