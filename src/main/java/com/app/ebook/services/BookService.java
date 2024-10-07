package com.app.ebook.services;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.ebook.exceptions.ResourceNotFoundException;
import com.app.ebook.model.Book;
import com.app.ebook.model.User;
import com.app.ebook.dto.BookUploadRequestDTO;
import com.app.ebook.repository.BookRepository;

import jakarta.validation.Valid;

@Service
public class BookService {
	
	private final BookRepository bookRepo;
    private final FileStorageService storageService;
    private final UserService userService;
	
	public BookService(BookRepository bookRepo, FileStorageService fileStorageService, UserService userService) {
		this.bookRepo=bookRepo;
		this.storageService = fileStorageService;
		this.userService = userService;
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
	
	public Book createBookWithFile(@Valid BookUploadRequestDTO bookDTO, MultipartFile file) throws IOException {
		User uploader = userService.getUserById(bookDTO.getUploaderId())
				.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + bookDTO.getUploaderId()));
        
        String pdfUrl = storageService.storeUserContent(uploader.getUsername(), bookDTO.getTitle(), file);
        Book book = new Book(bookDTO.getTitle(),bookDTO.getAuthor(),bookDTO.getGenre(),bookDTO.getDescription(),uploader,pdfUrl);
        return bookRepo.save(book);
	}

	public Book getBookById(Long id) {
		return bookRepo.findById(id).orElseThrow(()->new IllegalArgumentException("No such books found"));
	}
}
