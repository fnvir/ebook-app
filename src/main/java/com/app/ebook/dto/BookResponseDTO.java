package com.app.ebook.dto;

import java.time.LocalDateTime;

import com.app.ebook.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookResponseDTO {
    private Long bookId;
    private String title;
    private String author;
    private String genre;
    private String description;
    private User uploader;
    private String pdfUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
