package com.app.ebook.model;


import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews", uniqueConstraints = @UniqueConstraint(columnNames = {"bookId","reviewerId"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "reviewerId", referencedColumnName="userId")
    private User reviewer;
    
    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isAnonymous;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;
    
    private int rating;
    
    @Column(columnDefinition="TEXT")
    private String reviewText;

    @Column(updatable = false)
    @CreationTimestamp
    @ColumnDefault("current_timestamp")
    private LocalDateTime createdAt;

}
