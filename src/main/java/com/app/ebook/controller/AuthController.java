package com.app.ebook.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ebook.dto.UserDTO;
import com.app.ebook.dto.UserRegistrationDTO;
import com.app.ebook.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping(path="/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Register a new user", description = "Registers a new user with multipart form data")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "201", description = "User registered successfully"),
//	    @ApiResponse(responseCode = "400", description = "Bad request, validation failed")
	})
	public ResponseEntity<UserDTO> register(@ModelAttribute @Valid UserRegistrationDTO userDto) {
		return new ResponseEntity<>(authService.register(userDto),HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> postMethodName(@RequestBody Map<String, String> payload) {
		return ResponseEntity.ok(authService.login(payload));
	}
	
	
	
}
