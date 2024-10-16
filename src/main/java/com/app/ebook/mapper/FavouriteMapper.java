package com.app.ebook.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.app.ebook.dto.FavouriteResponseDTO;
import com.app.ebook.model.Favourite;

@Mapper(componentModel = "spring")
public interface FavouriteMapper {
    
    FavouriteMapper INSTANCE = Mappers.getMapper(FavouriteMapper.class);
    
    @Mapping(target="starredAt", source="createdAt")
    @Mapping(target="userId", source="id.userId")
    FavouriteResponseDTO favouriteToFavouriteResponseDTO(Favourite favourite);
    
    
}
