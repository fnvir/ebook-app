package com.app.ebook.exceptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException e) {
		List<Map<String, Object>> errors = e.getBindingResult().getFieldErrors().stream().map(fieldError -> {
			Map<String, Object> errorDetails = new HashMap<>();
			errorDetails.put("field", fieldError.getField());
			errorDetails.put("rejectedValue", fieldError.getRejectedValue());
			errorDetails.put("message", fieldError.getDefaultMessage());
			return errorDetails;
		}).toList();
		return ResponseEntity.badRequest().body(Collections.singletonMap("errors", errors));
	}

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleGlobalException(Exception ex) {
//        return new ResponseEntity<>("Internal server error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}