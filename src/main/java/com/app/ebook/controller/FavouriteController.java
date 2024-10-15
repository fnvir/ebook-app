package com.app.ebook.controller;

import com.app.ebook.model.Favourite;
import com.app.ebook.model.Favourite.FavouriteBookId;
import com.app.ebook.services.FavouriteService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favourites")
@RequiredArgsConstructor
@Tag(name = "Favourites", description = "API to manage a user's liked or starred books, representing their favorite reads or books saved for later.")
@SecurityRequirement(name = "bearerAuth")
public class FavouriteController {

    private final FavouriteService favouriteService;

    @GetMapping("/user/{userId}/all")
    public ResponseEntity<List<Favourite>> getFavouritesByUser(@PathVariable Long userId) {
        List<Favourite> favourites = favouriteService.getAllFavouritesByUser(userId);
        return ResponseEntity.ok(favourites);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Favourite>> getFavouritesByUser(@PathVariable Long userId, final Pageable pageable) {
        Page<Favourite> favourites = favouriteService.getFavouritesByUser(userId,pageable);
        return ResponseEntity.ok(favourites);
    }
    
    @PostMapping("/{userId}/{bookId}")
    public ResponseEntity<String> addFavourite(@PathVariable Long userId, @PathVariable Long bookId) {
        if(favouriteService.exists(userId, bookId))
            return ResponseEntity.badRequest().body("Book already exists in user's favourites");
        favouriteService.addFavourite(userId, bookId);
        return ResponseEntity.ok("Added successfully!");
    }

    @DeleteMapping("/{userId}/{bookId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> removeFavourite(@PathVariable Long userId, @PathVariable Long bookId) {
        favouriteService.removeFavourite(userId, bookId);
        return ResponseEntity.noContent().build();
    }

}
