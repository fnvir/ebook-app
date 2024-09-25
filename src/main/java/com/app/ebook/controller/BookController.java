package com.app.ebook.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.ebook.exceptions.ResourceNotFoundException;
import com.app.ebook.model.Book;
import com.app.ebook.model.User;
import com.app.ebook.payload.BookDTO;
import com.app.ebook.services.BookService;
import com.app.ebook.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {
	
	private BookService bookService;
    private final UserService userService;
	
	@Autowired
	public BookController(BookService bs, UserService us) {
		bookService=bs;
		userService=us;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Book>> getAllBooks() {
		return new ResponseEntity<List<Book>>(bookService.getAllBooks(),HttpStatus.OK);
	}
	
//	@PostMapping("/")
//	public ResponseEntity<Book> addBook(@RequestBody Book b) {
//		Book b2=bookService.addBook(b);
//		return new ResponseEntity<Book>(b, HttpStatus.CREATED);
//	}	
	
//	@PostMapping("/add")
//	public ResponseEntity<Book> addBook(@RequestParam("title") String title, @RequestParam("author") String author,
//			@RequestParam("genre") String genre, @RequestParam("uploader") String uploader,
//			@RequestParam("description") String description, @RequestParam("file") MultipartFile file) {
//		try {
//			String pdfUrl = bookService.savePdfFile(uploader, title, file);
//			Book newBook = new Book(title, author, genre, new User(uploader), description, pdfUrl, null, null);
//			Book savedBook = bookService.addBook(newBook);
//			return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
//		} catch (IOException e) {
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
	@PostMapping("/add")
    public ResponseEntity<Book> addBook(
            @Valid @ModelAttribute BookDTO bookRequestDTO,
            @RequestPart("file") MultipartFile file) throws IOException {

        Book savedBook = bookService.createBookWithFile(bookRequestDTO, file);

        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @GetMapping("/pages")
    public Page<Book> getBooksPaginated(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        return bookService.getBooksPaginated(page, size);
    }

}
