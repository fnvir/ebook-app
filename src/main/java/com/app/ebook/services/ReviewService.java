package com.app.ebook.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.ebook.dto.ReviewRequestDTO;
import com.app.ebook.dto.ReviewResponseDTO;
import com.app.ebook.model.Book;
import com.app.ebook.model.Review;
import com.app.ebook.model.User;
import com.app.ebook.repository.BookRepository;
import com.app.ebook.repository.ReviewRepository;
import com.app.ebook.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewService {
	
	private final ReviewRepository reviewRepo;
    private final BookRepository bookRepo;
    private final UserRepository userRepo;
	
    public Review addReview(ReviewRequestDTO reviewRequest) {
        Book book = bookRepo.findById(reviewRequest.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        
		User reviewer = userRepo.findById(reviewRequest.getReviewerId())
				.orElseThrow(() -> new RuntimeException("User not found"));
		
        Review review = Review.builder()
        				.book(book)
        				.rating(reviewRequest.getRating())
        				.reviewText(reviewRequest.getReviewText())
        				.reviewer(reviewer)
        				.build();
        
        if (reviewRequest.getIsAnonymous()!=null) {
        	review.setAnonymous(reviewRequest.getIsAnonymous());
        }

        review = reviewRepo.save(review);

        return review;
    }
	
    public List<Review> getAllReviews() {
        return reviewRepo.findAll();
    }

	public Page<ReviewResponseDTO> getReviewsWithoutReviewerAndBookUploader(Long id, final Pageable pageable) {
		return reviewRepo.findByReviewerWithoutUploader(id,pageable)
			.map(r->
				ReviewResponseDTO.builder()
				.reviewId(r.getReviewId())
				.book(new Book(r.getBookId(), r.getTitle(), r.getAuthor(), r.getGenre(), r.getDescription(),
						null, null, r.getBookCreatedAt(), r.getBookUpdatedAt()))
				.isAnonymous(r.getIsAnonymous())
				.rating(r.getRating())
				.createdAt(r.getCreatedAt())
				.reviewText(r.getReviewText())
				.build()
			);
	}
	
	public List<ReviewResponseDTO> getReviewsOfBook(Long id) {
		return reviewRepo.findByBookBookId(id).stream().map(review -> 
	        ReviewResponseDTO.builder()
	        .reviewId(review.getReviewId())
	        .reviewer(review.getReviewer())
	        .isAnonymous(review.getIsAnonymous())
	        .rating(review.getRating())
	        .reviewText(review.getReviewText())
	        .createdAt(review.getCreatedAt())
	        .build()
		).toList();
	}
	
	public Page<ReviewResponseDTO> getReviewsOfBookPageable(Long id, final Pageable pageable) {
		return reviewRepo.findByBookBookId(id,pageable)
			.map(review -> 
				ReviewResponseDTO.builder()
				.reviewId(review.getReviewId())
				.reviewer(review.getReviewer())
				.isAnonymous(review.getIsAnonymous())
				.rating(review.getRating())
				.reviewText(review.getReviewText())
				.createdAt(review.getCreatedAt())
				.build()
			);
	}
	
    // Update review
	public Review updateReview(Long reviewId, ReviewRequestDTO reviewRequest) {
        Review review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        if (review.getReviewer() != null && !review.getReviewer().getUserId().equals(reviewRequest.getReviewerId())) {
            throw new RuntimeException("You can only update your own review");
        }
        review.setRating(reviewRequest.getRating());
        review.setReviewText(reviewRequest.getReviewText());
        return reviewRepo.save(review);
    }
    
    public void deleteReview(Long reviewId, Long reviewerId) {
        Review review = reviewRepo.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found"));

        if (review.getReviewer() != null && !review.getReviewer().getUserId().equals(reviewerId)) {
            throw new RuntimeException("You can only delete your own review");
        }

        reviewRepo.delete(review);
    }
	
	
}