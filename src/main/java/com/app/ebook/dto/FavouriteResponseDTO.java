package com.app.ebook.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteResponseDTO {
    private Long userId;
    private BookResponseDTO book;
    private LocalDateTime starredAt;
}
