package com.app.ebook.services;

import com.app.ebook.model.Favourite;
import com.app.ebook.model.Favourite.FavouriteBookId;
import com.app.ebook.repository.FavouriteRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FavouriteService {

    private final FavouriteRepository repository;
    
    public Favourite addFavourite(Favourite favourite) {
        return repository.save(favourite);
    }
    
    @PreAuthorize("#u == authentication.principal.userId")
    public void addFavourite(@P("u") Long userId, Long bookId) {
        repository.saveFavourite(userId, bookId);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN') OR #u == authentication.principal.userId")
    public void removeFavourite(@P("u") Long userId, Long bookId) {
        repository.deleteById(new FavouriteBookId(userId, bookId));
    }
    
    public boolean exists(Long userId, Long bookId) {
        return exists(new FavouriteBookId(userId, bookId));
    }
    
    public boolean exists(FavouriteBookId id) {
        return repository.existsById(id);
    }

    public List<Favourite> getAllFavouritesByUser(Long userId) {
        return repository.findAllByUserUserId(userId);
    }

    public Page<Favourite> getFavouritesByUser(Long userId, final Pageable pageable) {
        return repository.findByUserUserId(userId,pageable);
    }
}
