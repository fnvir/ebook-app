package com.app.ebook.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.app.ebook.model.Review;


@Mapper(componentModel = "spring")
public interface UserMapper {
    
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	
}
