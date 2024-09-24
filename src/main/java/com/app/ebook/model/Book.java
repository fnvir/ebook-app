package com.app.ebook.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private String title;
    private String author;
    private String genre;
    @ManyToOne
    @JoinColumn(name = "uploader_id")
    private User uploader;
    private String description;
    private String pdfUrl;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

	@UpdateTimestamp
    private LocalDateTime updatedAt;
    
    public Book(String title, String author, String genre, User uploader, String description,
			String pdfUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.uploader = uploader;
		this.description = description;
		this.pdfUrl = pdfUrl;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
    
}
