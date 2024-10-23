package com.app.ebook.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.app.ebook.dto.ReviewRequestDTO;
import com.app.ebook.dto.ReviewResponseDTO;
import com.app.ebook.model.Review;
import com.app.ebook.repository.ReviewRepository.ReviewsByBook;
import com.app.ebook.services.BookService;
import com.app.ebook.services.UserService;

@Mapper(componentModel = "spring", uses = {BookService.class, UserService.class})
public interface ReviewMapper {
    
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    @Mapping(source="anonymous", target="isAnonymous")
    ReviewResponseDTO reviewToReviewResponseDTO(Review review);
    ReviewResponseDTO reviewsByBookToReviewResponseDTO(ReviewsByBook review);
    @Mapping(source = "bookId", target="book")
    @Mapping(source = "reviewerId", target="reviewer")
    @Mapping(target="anonymous", ignore=true)
    Review reviewRequestToReview(ReviewRequestDTO dto);
}
