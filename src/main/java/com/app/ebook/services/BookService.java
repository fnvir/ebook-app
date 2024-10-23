package com.app.ebook.services;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.ebook.exceptions.ResourceNotFoundException;
import com.app.ebook.mapper.BookMapper;
import com.app.ebook.model.Book;
import com.app.ebook.model.User;
import com.app.ebook.dto.BookResponseDTO;
import com.app.ebook.dto.BookUploadRequestDTO;
import com.app.ebook.repository.BookRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {
	
	private final BookRepository bookRepo;
    private final FileStorageService storageService;
    private final UserService userService;
    private final BookMapper bookMapper;
	
//	public BookService(BookRepository bookRepo, FileStorageService fileStorageService, UserService userService, BookMapper bookMapper) {
//		this.bookRepo=bookRepo;
//		this.storageService = fileStorageService;
//		this.userService = userService;
//        this.bookMapper = bookMapper;
//	}
	
	public List<BookResponseDTO> getAllBooks(){
		return bookRepo.findAll().stream().map(bookMapper::bookToBookResponseDTO).toList();
	}
	
    public Page<BookResponseDTO> getBooksPaginated(final Pageable pageable) {
        return bookRepo.findAll(pageable).map(bookMapper::bookToBookResponseDTO);
    }
	
	public Book addBook(Book b) {
		return bookRepo.save(b);
	}
	
	public BookResponseDTO createBookWithFile(BookUploadRequestDTO bookDTO) throws IOException {
		User uploader = userService.getUserById(bookDTO.getUploaderId());
		MultipartFile file = bookDTO.getFile();
        String pdfUrl = storageService.storeUserContent(uploader.getUsername(), bookDTO.getTitle(), file);
        Book book = bookMapper.bookUploadRequestToBook(bookDTO);
        book.setUploader(uploader);
        book.setPdfUrl(pdfUrl);
        return bookMapper.bookToBookResponseDTO(bookRepo.save(book));
	}
	
	public Book findBookById(Long bookId) {
	    return bookRepo.findById(bookId).orElseThrow(()->new IllegalArgumentException("No such books found"));
	}

	public BookResponseDTO getBookById(Long id) {
		Book b = findBookById(id);
		return bookMapper.bookToBookResponseDTO(b);
	}
}
