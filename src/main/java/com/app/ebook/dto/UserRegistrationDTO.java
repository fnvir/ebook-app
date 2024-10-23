package com.app.ebook.dto;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "John")
    @NotNull(message = "First name is required")
    @Size(max = 50, message = "First name must be less than 50 characters")
    private String firstname;

    @Schema(example = "Cena")
    @NotNull(message = "Last name is required")
    @Size(max = 50, message = "Last name must be less than 50 characters")
    private String lastname;

    @Schema(example = "johncena")
    @NotNull(message = "Username is required")
    @Size(max = 50, message = "Username must be less than 50 characters")
    private String username;

    @Schema(example = "john@cena.com")
    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(example = "Password1234")
    @NotNull(message = "Password is required")
    private String password;
    
    private MultipartFile picture;
}
