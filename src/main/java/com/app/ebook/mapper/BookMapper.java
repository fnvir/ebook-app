package com.app.ebook.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.app.ebook.dto.BookResponseDTO;
import com.app.ebook.dto.BookUploadRequestDTO;
import com.app.ebook.model.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {
    
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    
    BookResponseDTO bookToBookResponseDTO(Book book);
    
    @Mapping(target = "bookId", ignore=true)
    @Mapping(target = "uploader", ignore=true)
    @Mapping(target = "pdfUrl", ignore=true)
    @Mapping(target = "createdAt", ignore=true)
    @Mapping(target = "updatedAt", ignore=true)
    Book bookUploadRequestToBook(BookUploadRequestDTO bookUpload);
    
}
