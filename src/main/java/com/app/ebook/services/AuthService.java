package com.app.ebook.services;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.ebook.model.Role;
import com.app.ebook.model.User;
import com.app.ebook.dto.UserDTO;
import com.app.ebook.dto.UserRegistrationDTO;
import com.app.ebook.mapper.UserMapper;
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
	private final UserMapper userMapper;

	public UserDTO register(UserRegistrationDTO regDTO) {
	    return register(regDTO, Role.MEMBER);
	}
	
	public UserDTO adminRegister(UserRegistrationDTO regDTO) {
	    return register(regDTO, Role.ADMIN);
	}
	
	public UserDTO guestRegister(UserRegistrationDTO regDTO) {
	    return register(regDTO, Role.GUEST);
	}
	
	public UserDTO register(UserRegistrationDTO regDTO, Role role) {
	    if(repository.existsByEmailOrUsername(regDTO.getEmail(), regDTO.getUsername()))
	        throw new IllegalArgumentException("Username or Email already in use!");
	    User user = userMapper.userRegDTO_toUser(regDTO);
	    if(regDTO.getPicture()!=null)
    	    user.setPicturePath(storageService.storeFile(
    	            regDTO.getPicture(),
                    regDTO.getUsername() + "_" + UUID.randomUUID().toString().replace("-", "")));
	    user.setRole(role);
	    user.setPassword(encoder.encode(regDTO.getPassword()));
		user=repository.save(user);
		return userMapper.userToUserDTO(user);
	}

	public Map<String, String> login(Map<String, String> payload) {
		authManager.authenticate(
			new UsernamePasswordAuthenticationToken(payload.get("identifier"), payload.get("password"))
		);
		var user=repository.findByEmailOrUsername(payload.get("identifier")).orElseThrow(()->new UsernameNotFoundException("User not found"));
		var jwt=jwtUtil.generateToken(user);
		var res=Collections.singletonMap("token", jwt);
		return res;
	}

}
