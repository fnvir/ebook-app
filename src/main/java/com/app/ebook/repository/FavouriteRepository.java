package com.app.ebook.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.ebook.model.Favourite;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite,Favourite.FavouriteBookId> {
    
    @Query("SELECT f FROM Favourite f JOIN FETCH f.book b LEFT JOIN FETCH b.uploader WHERE f.id.userId=?1")
    List<Favourite> findAllByUserUserId(Long userId);

    @Query("SELECT f FROM Favourite f JOIN FETCH f.book b LEFT JOIN FETCH b.uploader WHERE f.id.userId=?1")
    Page<Favourite> findByUserUserId(Long userId, Pageable pageable);
    
    @Modifying
    @Query(value="INSERT INTO favourites (user_id, book_id) VALUES (?1, ?2)",nativeQuery=true)
    void saveFavourite(Long userId, Long bookId);

}
