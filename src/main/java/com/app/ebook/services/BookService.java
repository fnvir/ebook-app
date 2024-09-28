package com.app.ebook.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.ebook.exceptions.ResourceNotFoundException;
import com.app.ebook.model.Book;
import com.app.ebook.model.User;
import com.app.ebook.payload.BookDTO;
import com.app.ebook.repository.BookRepository;

import jakarta.validation.Valid;

@Service
public class BookService {
	
	private final BookRepository bookRepo;
    private final FileStorageServiceOld fileStorageService;
    private final UserService userService;
	
	public BookService(BookRepository bookRepo, FileStorageServiceOld fileStorageService, UserService userService) {
		this.bookRepo=bookRepo;
		this.fileStorageService = fileStorageService;
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
	
	public Book createBookWithFile(@Valid BookDTO bookDTO, MultipartFile file) throws IOException {
        Optional<User> uploader = userService.getUserById(bookDTO.getUploaderId());
        if(uploader.isEmpty())
             throw new ResourceNotFoundException("User not found with ID: " + bookDTO.getUploaderId());
        
        String sanitizedTitle=bookDTO.getTitle().replaceAll("[^a-zA-Z0-9\\-]", "_");
        Book book = new Book(sanitizedTitle,bookDTO.getAuthor(),bookDTO.getGenre(),bookDTO.getDescription(),uploader.get());
        
        String pdfUrl = fileStorageService.savePdfFile(uploader.get().getUsername(), sanitizedTitle, file);
        book.setPdfUrl(pdfUrl);
        
        return bookRepo.save(book);
	}
	
	
}
