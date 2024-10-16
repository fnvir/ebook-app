package com.app.ebook.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.app.ebook.dto.UserDTO;
import com.app.ebook.dto.UserRegistrationDTO;
import com.app.ebook.model.User;


@Mapper(componentModel = "spring")
public interface UserMapper {
    
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
    @Mapping(target = "firstName", source = "firstname")
    @Mapping(target = "lastName", source = "lastname")
	@Mapping(source = "picture", ignore = true, target = "picturePath")
	@Mapping(target="password", ignore=true)
	@Mapping(target="role", ignore=true)
    @Mapping(target="userId", ignore=true)
    @Mapping(target="profileViews", ignore=true)
	User userRegDTO_toUser(UserRegistrationDTO dto);
	UserDTO userToUserDTO(User user);
	
}
