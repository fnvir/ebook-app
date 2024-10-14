package com.app.ebook.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.ebook.model.Book;
import com.app.ebook.dto.BookResponseDTO;
import com.app.ebook.dto.BookUploadRequestDTO;
import com.app.ebook.services.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {
	
	private BookService bookService;
	
	public BookController(BookService bs) {
		bookService=bs;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
		return new ResponseEntity<>(bookService.getAllBooks(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BookResponseDTO> getAllBooks(@PathVariable Long id) {
		return ResponseEntity.ok(bookService.getBookById(id));
	}
	
    @GetMapping
    public Page<BookResponseDTO> getBooksPaginated(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        return bookService.getBooksPaginated(page, size);
    }
	
//	@PostMapping
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
	
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BookResponseDTO> addBook(
    		@Valid @ModelAttribute BookUploadRequestDTO bookRequestDTO) throws IOException {

        BookResponseDTO savedBook = bookService.createBookWithFile(bookRequestDTO);

        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

}
