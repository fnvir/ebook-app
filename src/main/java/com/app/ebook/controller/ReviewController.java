package com.app.ebook.controller;

import com.app.ebook.dto.ReviewRequestDTO;
import com.app.ebook.dto.ReviewResponseDTO;
import com.app.ebook.model.Review;
import com.app.ebook.services.ReviewService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "reviews", description = "the review API")
public class ReviewController {
	
	private final ReviewService reviewService;
	
    @GetMapping
    public List<ReviewResponseDTO> getAllReviews() {
        return reviewService.getAllReviews();
    }
    
	@GetMapping("/user/{id}")
	public ResponseEntity<Page<ReviewResponseDTO>> getReviewsByUser(@PathVariable Long id, final Pageable pageable) {
		return ResponseEntity.ok(reviewService.getReviewsWithoutReviewerAndBookUploader(id, pageable));
	}
    
    @GetMapping("/book/{id}/all")
    public List<ReviewResponseDTO> getReviewsOfBook(@PathVariable Long id){
    	return reviewService.getReviewsOfBook(id);
    }
    
	@GetMapping("/book/{id}")
	public ResponseEntity<Page<ReviewResponseDTO>> getReviewsOfBookPageable(@PathVariable Long id,
			final Pageable pageable) {
		return ResponseEntity.ok(reviewService.getReviewsOfBookPageable(id, pageable));
	}
	
	// create
    @PostMapping("/book/{bookId}")
	public ResponseEntity<Review> createReview(@PathVariable Long bookId,
											   @Valid @RequestBody ReviewRequestDTO reviewRequestDTO) {
        reviewRequestDTO.setBookId(bookId);
        Review createdReview = reviewService.addReview(reviewRequestDTO);
        return new ResponseEntity<>(createdReview,HttpStatus.CREATED);
    }
    
    // Update review
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> updateReview(@PathVariable Long reviewId,
                                                          @Valid @RequestBody ReviewRequestDTO reviewRequestDTO) {
    	ReviewResponseDTO updatedReview = reviewService.updateReview(reviewId, reviewRequestDTO);
        return ResponseEntity.ok(updatedReview);
    }
    
    // Delete review
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
    	Long reviewerId = reviewService.getReviewerIDById(reviewId);
    	reviewService.deleteReview(reviewId,reviewerId);
        return ResponseEntity.noContent().build();
    }
    
}