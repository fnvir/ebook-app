package com.app.ebook.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.ebook.model.Role;
import com.app.ebook.model.User;
import com.app.ebook.payload.UserDTO;
import com.app.ebook.repository.UserRepository;
import com.app.ebook.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final UserRepository repository;
	private final PasswordEncoder encoder;
	private final AuthenticationManager authManager;
	private final JwtUtil jwtUtil;

	public Map<String, Object> register(UserDTO userDto) {
		User user = User.builder()
				.firstName(userDto.getFirstName())
				.lastName(userDto.getLastName())
				.email(userDto.getEmail())
				.password(encoder.encode(userDto.getPassword()))
				.username(userDto.getUsername())
				.role(Role.MEMBER)
				.build();
		user=repository.save(user);
		var jwt=jwtUtil.generateToken(user);
		var res=new HashMap<String, Object>();
		res.put("token", jwt);
		res.put("user", user);
		return res;
	}

	public Map<String, String> login(Map<String, String> payload) {
		authManager.authenticate(
			new UsernamePasswordAuthenticationToken(payload.get("email"), payload.get("password"))
		);
		var user=repository.findByEmailOrUsername(payload.get("email")).orElseThrow(()->new RuntimeException("User not found"));
		var jwt=jwtUtil.generateToken(user);
		var res=new HashMap<String, String>();
		res.put("token", jwt);
		return res;
	}

}
