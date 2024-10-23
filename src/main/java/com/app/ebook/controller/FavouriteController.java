package com.app.ebook.controller;

import com.app.ebook.dto.FavouriteResponseDTO;
import com.app.ebook.model.Favourite;
import com.app.ebook.model.Favourite.FavouriteBookId;
import com.app.ebook.services.FavouriteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.SortDefault;
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

    @Operation(summary = "Get all favourites of a user")
    @GetMapping("/user/{userId}/all")
    public ResponseEntity<List<FavouriteResponseDTO>> getFavouritesByUser(@PathVariable Long userId) {
        var favourites = favouriteService.getAllFavouritesByUser(userId);
        return ResponseEntity.ok(favourites);
    }
    
    @Operation(summary = "Get paginated favourites of a user")
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<FavouriteResponseDTO>> getFavouritesByUser(@PathVariable Long userId,
            @SortDefault(sort = "createdAt", direction = Direction.DESC) @ParameterObject final Pageable pageable) {
        var favourites = favouriteService.getFavouritesByUser(userId, pageable);
        return ResponseEntity.ok(favourites);
    }
    
    @Operation(summary = "Add a book to a user's favourites")
    @ApiResponse(responseCode = "200", description = "Added as favourite successfully!", content = @Content)
    @ApiResponse(responseCode = "400", description = "Book already exists in user's favourites", content=@Content)
    @PostMapping("/{userId}/{bookId}")
    public ResponseEntity<String> addFavourite(@PathVariable Long userId, @PathVariable Long bookId) {
        if(favouriteService.exists(userId, bookId))
            return ResponseEntity.badRequest().body("Book already exists in user's favourites");
        favouriteService.addFavourite(userId, bookId);
        return ResponseEntity.ok("Added successfully!");
    }

    @Operation(summary = "Remove a book from a user's favourites")
    @ApiResponse(responseCode = "204")
    @DeleteMapping("/{userId}/{bookId}")
    public ResponseEntity<Void> removeFavourite(@PathVariable Long userId, @PathVariable Long bookId) {
        favouriteService.removeFavourite(userId, bookId);
        return ResponseEntity.noContent().build();
    }

}
