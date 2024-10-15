package com.app.ebook.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Liked / starred books of an user representing a collection of favorite books or books saved to read later
 */
@Entity
@Table(name = "favourites")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favourite {
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FavouriteBookId implements Serializable {

        private static final long serialVersionUID = 1L;

        @Column(name = "user_id")
        private Long userId;

        @Column(name = "book_id")
        private Long bookId;
    }
    
    @EmbeddedId
    private FavouriteBookId id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("bookId")
    @JoinColumn(name = "book_id", referencedColumnName = "bookId")
    private Book book;

    @CreationTimestamp
    @Column(updatable = false)
    @ColumnDefault("current_timestamp")
    private LocalDateTime createdAt;

}
