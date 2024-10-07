package com.app.ebook.dto;

import lombok.Data;

@Data
public class BookUploadRequestDTO {
    private String title;
    private String author;
    private String genre;
    private String description;
    private Long uploaderId;
}
