package com.app.ebook.dto;

import java.time.LocalDateTime;

import com.app.ebook.model.Book;
import com.app.ebook.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewResponseDTO {
	Long reviewId;
    User reviewer;
    boolean isAnonymous;
    Book book;
    int rating;
    String reviewText;
    LocalDateTime createdAt;
}
