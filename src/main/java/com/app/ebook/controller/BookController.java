package com.app.ebook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.ebook.model.Book;
import com.app.ebook.services.BookService;

@RestController
@RequestMapping("/books")
public class BookController {
	
	private BookService bookService;
	
	@Autowired
	public BookController(BookService bs) {
		bookService=bs;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Book>> getAllMovies() {
		return new ResponseEntity<List<Book>>(bookService.getAllBooks(),HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<Book> addBook(@RequestBody Book b) {
		Book b2=bookService.addBook(b);
		return new ResponseEntity<Book>(b, HttpStatus.CREATED);
	}
	
    @GetMapping("/pages")
    public Page<Book> getBooksPaginated(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        return bookService.getBooksPaginated(page, size);
    }

}
