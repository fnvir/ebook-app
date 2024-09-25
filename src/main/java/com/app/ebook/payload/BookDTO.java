package com.app.ebook.payload;

import lombok.Data;

@Data
public class BookDTO {
    private String title;
    private String author;
    private String genre;
    private String description;
    private Long uploaderId;
}
