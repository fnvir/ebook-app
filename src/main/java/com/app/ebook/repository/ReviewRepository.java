package com.app.ebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.ebook.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}