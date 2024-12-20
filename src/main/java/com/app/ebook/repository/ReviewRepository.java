package com.app.ebook.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.ebook.model.Review;
import com.app.ebook.model.User;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	@Query("SELECT r FROM Review r LEFT JOIN FETCH r.reviewer LEFT JOIN FETCH r.book b LEFT JOIN FETCH b.uploader")
	List<Review> findAll();
	
	@Query("SELECT r.reviewer.userId FROM Review r where r.reviewId = ?1")
	Optional<Long> findReviewerIdByReviewId(Long reviewId);
	
	List<ReviewsByBook> findByBookBookId(Long bookId);
	
	Page<ReviewsByBook> findByBookBookId(Long bookId, Pageable pageable);
    
//    List<Review> findByReviewerUserId(Long reviewerId);
	
    @Query(value="SELECT r.review_id, r.is_anonymous, r.rating, r.review_text, r.created_at, "
    		+ "b.book_id, b.title, b.author, b.genre, b.description, "
    		+ "b.created_at AS book_created_at, b.updated_at AS book_updated_at "
    		+ "FROM reviews r "
    		+ "LEFT JOIN books b ON "
    		+ "r.book_id = b.book_id "
    		+ "WHERE r.reviewer_id = :reviewerId", 
    		countQuery = "SELECT COUNT(*) FROM reviews r WHERE r.reviewer_id=:reviewerId",
    		nativeQuery = true)
    Page<ReviewsByUser> findByReviewerWithoutUploader(@Param("reviewerId") Long reviewerId, Pageable pageable);

    /**
     * Projection for {@link com.app.ebook.model.Review}
     */
    public static interface ReviewsByBook {
        Long getReviewId();
        User getReviewer();
        boolean getIsAnonymous();
        int getRating();
        String getReviewText();
        LocalDateTime getCreatedAt();
    }

    /**
     * Projection for {@link com.app.ebook.model.Review}
     */
    public static interface ReviewsByUser {
        Long getReviewId();
        boolean getIsAnonymous();
        int getRating();
        String getReviewText();
        LocalDateTime getCreatedAt();
        Long getBookId();
        String getTitle();
        String getAuthor();
        String getGenre();
        String getDescription();
        LocalDateTime getBookCreatedAt();
        LocalDateTime getBookUpdatedAt();
    }
	
}