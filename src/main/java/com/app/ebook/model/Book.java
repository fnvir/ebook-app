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
    private String description;
    @ManyToOne
    @JoinColumn(name = "uploader_id", referencedColumnName = "userId")
    private User uploader;
    private String pdfUrl;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

	@UpdateTimestamp
    private LocalDateTime updatedAt;
    
    public Book(String title, String author, String genre, String description, User uploader, String pdfUrl) {
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.uploader = uploader;
		this.description = description;
		this.pdfUrl=pdfUrl;
	}
    
}
