package com.app.ebook.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.ebook.model.User;
import com.app.ebook.dto.UserDTO;
import com.app.ebook.dto.UserRegistrationDTO;
import com.app.ebook.repository.UserRepository;
import com.app.ebook.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final UserRepository repository;
	private final PasswordEncoder encoder;
	private final AuthenticationManager authManager;
	private final FileStorageService storageService;
	private final JwtUtil jwtUtil;

	public UserDTO register(UserRegistrationDTO regDTO) {
		User user = User.builder()
				.firstName(regDTO.getFirstname())
				.lastName(regDTO.getLastname())
				.email(regDTO.getEmail())
				.password(encoder.encode(regDTO.getPassword()))
				.username(regDTO.getUsername())
				.role(regDTO.getRole())
				.picturePath(storageService.storeFile(regDTO.getPicture(),
						regDTO.getUsername() + "_" + UUID.randomUUID().toString().replace("-", "")))
				.build();
		user=repository.save(user);
		return new UserDTO(user.getUserId(), user.getFirstName(), user.getLastName(), user.getUsername(),
				user.getEmail(), user.getPicturePath(), user.getProfileViews());
	}

	public Map<String, String> login(Map<String, String> payload) {
		authManager.authenticate(
			new UsernamePasswordAuthenticationToken(payload.get("email"), payload.get("password"))
		);
		var user=repository.findByEmailOrUsername(payload.get("email")).orElseThrow(()->new UsernameNotFoundException("User not found"));
		var jwt=jwtUtil.generateToken(user);
		var res=Collections.singletonMap("token", jwt);
		return res;
	}

}
