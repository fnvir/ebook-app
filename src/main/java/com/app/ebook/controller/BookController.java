package com.app.ebook.controller;

import java.io.IOException;
import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.app.ebook.dto.BookResponseDTO;
import com.app.ebook.dto.BookUploadRequestDTO;
import com.app.ebook.services.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "API for handling books")
@RequiredArgsConstructor
public class BookController {
	
	private final BookService bookService;
	   
    @GetMapping
    @Operation(summary = "Get books paginated")
    public Page<BookResponseDTO> getBooksPaginated(@ParameterObject final Pageable pageable) {
        return bookService.getBooksPaginated(pageable);
    }
    
	@GetMapping("/all")
	@Operation(summary="Get all books as a list (non-paginated)")
	public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
		return new ResponseEntity<>(bookService.getAllBooks(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Get a book by id")
	public ResponseEntity<BookResponseDTO> getAllBooks(@PathVariable Long id) {
		return ResponseEntity.ok(bookService.getBookById(id));
	}
	
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Add a new book", description = "Add a new book with a file upload")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Book created successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request, validation failed")
    })    
    public ResponseEntity<BookResponseDTO> addBook(
    		@Valid @ModelAttribute BookUploadRequestDTO bookRequestDTO) throws IOException {

        BookResponseDTO savedBook = bookService.createBookWithFile(bookRequestDTO);

        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

}
