package com.app.ebook.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import com.app.ebook.dto.ReviewRequestDTO;
import com.app.ebook.dto.ReviewResponseDTO;
import com.app.ebook.mapper.ReviewMapper;
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
    private final ReviewMapper reviewMapper;
	
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
    
	public Long getReviewerIDById(Long reviewId) {
		return reviewRepo.findReviewerIdByReviewId(reviewId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Review ID"));
    }
	
    public List<ReviewResponseDTO> getAllReviews() {
        return reviewRepo.findAll().stream().map(reviewMapper::reviewToReviewResponseDTO).toList();
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
		return reviewRepo.findByBookBookId(id).stream().map(reviewMapper::reviewsByBookToReviewResponseDTO).toList();
	}
	
	public Page<ReviewResponseDTO> getReviewsOfBookPageable(Long id, final Pageable pageable) {
		return reviewRepo.findByBookBookId(id,pageable).map(reviewMapper::reviewsByBookToReviewResponseDTO);
	}
	
//	@PreAuthorize("#r.reviewerId == authentication.principal.userId")
	public ReviewResponseDTO updateReview(Long reviewId, @P("r") ReviewRequestDTO reviewRequest) {
        Review review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        if (review.getReviewer() != null && !review.getReviewer().getUserId().equals(reviewRequest.getReviewerId())) {
            throw new RuntimeException("You can only update your own review");
        }
        review.setRating(reviewRequest.getRating());
        review.setReviewText(reviewRequest.getReviewText());
        review.setAnonymous(reviewRequest.getIsAnonymous());
        return reviewMapper.reviewToReviewResponseDTO(reviewRepo.save(review));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR #r == authentication.principal.userId")
    public void deleteReview(Long reviewId, @P("r") Long reviewerId) {
        reviewRepo.deleteById(reviewId);
    }
    
}