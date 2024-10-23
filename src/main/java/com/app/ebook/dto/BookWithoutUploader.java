package com.app.ebook.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BookWithoutUploader {
    private Long bookId;
    private String title;
    private String author;
    private String genre;
    private String description;
    private String pdfUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
