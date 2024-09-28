package com.app.ebook.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ebook.payload.UserDTO;
import com.app.ebook.services.AuthService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping("/register/")
	public ResponseEntity<Map<String,Object>> register(@RequestBody UserDTO userDto) {
		return ResponseEntity.status(201).body(authService.register(userDto));
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> postMethodName(@RequestBody Map<String, String> payload) {
		return ResponseEntity.ok(authService.login(payload));
	}
	
	
	
}
