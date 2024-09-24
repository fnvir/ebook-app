package com.app.ebook.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.app.ebook.model.Book;
import com.app.ebook.repository.BookRepository;

@Service
public class BookService {
	
	private BookRepository bookRepo;
	
	@Autowired
	public BookService(BookRepository bookRepo) {
		this.bookRepo=bookRepo;
	}
	
	public List<Book> getAllBooks(){
		return bookRepo.findAll();
	}
	
    public Page<Book> getBooksPaginated(int page, int size) {
        return bookRepo.findAll(PageRequest.of(page, size));
    }
	
	public Book addBook(Book b) {
		return bookRepo.save(b);
	}
	
}
