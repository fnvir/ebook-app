package com.app.ebook.dto;

import org.springframework.web.multipart.MultipartFile;

import com.app.ebook.model.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {
    @NotNull(message = "First name is required")
    @Size(max = 50, message = "First name must be less than 50 characters")
    private String firstname;

    @NotNull(message = "Last name is required")
    @Size(max = 50, message = "Last name must be less than 50 characters")
    private String lastname;

    @NotNull(message = "Username is required")
    @Size(max = 50, message = "Username must be less than 50 characters")
    private String username;

    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Password is required")
    private String password;

    private Role role;

    private MultipartFile picture;
}
