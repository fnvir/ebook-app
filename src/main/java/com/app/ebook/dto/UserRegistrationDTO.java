package com.app.ebook.dto;

import org.springframework.web.multipart.MultipartFile;

import com.app.ebook.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {
	private String firstname;
	private String lastname;
	private String username;
	private String email;
	private String password;
	private Role role;
	private MultipartFile picture;
}
