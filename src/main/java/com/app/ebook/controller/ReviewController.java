package com.app.ebook.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ebook.dto.ReviewRequestDTO;
import com.app.ebook.dto.ReviewResponseDTO;
import com.app.ebook.model.Review;
import com.app.ebook.services.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Reviews", description = "API for handling reviews")
public class ReviewController {
	
	private final ReviewService reviewService;
	
    @GetMapping
    public List<ReviewResponseDTO> getAllReviews() {
        return reviewService.getAllReviews();
    }
    
	@GetMapping("/user/{id}")
	public ResponseEntity<Page<ReviewResponseDTO>> getReviewsByUser(@PathVariable Long id, @ParameterObject final Pageable pageable) {
		return ResponseEntity.ok(reviewService.getReviewsWithoutReviewerAndBookUploader(id, pageable));
	}
    
    @GetMapping("/book/{id}/all")
    public List<ReviewResponseDTO> getReviewsOfBook(@PathVariable Long id){
    	return reviewService.getReviewsOfBook(id);
    }
    
	@GetMapping("/book/{id}")
	public ResponseEntity<Page<ReviewResponseDTO>> getReviewsOfBookPageable(@PathVariable Long id,
	        @ParameterObject final Pageable pageable) {
		return ResponseEntity.ok(reviewService.getReviewsOfBookPageable(id, pageable));
	}
	
	// create
    @PostMapping("/book/{bookId}")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "201", description = "Review added successfully")
	public ResponseEntity<Review> createReview(@PathVariable Long bookId,
											   @Valid @RequestBody ReviewRequestDTO reviewRequestDTO) {
        reviewRequestDTO.setBookId(bookId);
        Review createdReview = reviewService.addReview(reviewRequestDTO);
        return new ResponseEntity<>(createdReview,HttpStatus.CREATED);
    }
    
    // Update review
    @PutMapping("/{reviewId}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ReviewResponseDTO> updateReview(@PathVariable Long reviewId,
                                                          @Valid @RequestBody ReviewRequestDTO reviewRequestDTO) {
    	ReviewResponseDTO updatedReview = reviewService.updateReview(reviewId, reviewRequestDTO);
        return ResponseEntity.ok(updatedReview);
    }
    
    // Delete review
    @DeleteMapping("/{reviewId}")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "204", description = "Review deleted successfully")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
    	Long reviewerId = reviewService.getReviewerIDById(reviewId);
    	reviewService.deleteReview(reviewId,reviewerId);
        return ResponseEntity.noContent().build();
    }
    
}