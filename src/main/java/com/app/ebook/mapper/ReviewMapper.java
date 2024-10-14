package com.app.ebook.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.app.ebook.dto.ReviewResponseDTO;
import com.app.ebook.model.Review;
import com.app.ebook.repository.ReviewRepository.ReviewsByBook;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    // Map Review entity to ReviewResponseDTO
    ReviewResponseDTO reviewToReviewResponseDTO(Review review);
    
    ReviewResponseDTO reviewsByBookToReviewResponseDTO(ReviewsByBook review);
}
