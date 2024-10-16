package com.app.ebook.model;


import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    @ColumnDefault("current_timestamp")
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
