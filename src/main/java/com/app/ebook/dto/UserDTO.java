package com.app.ebook.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.app.ebook.model.User}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "First name is required")
    @Size(max = 50, message = "First name must be less than 50 characters")
    private String firstName;

    @NotNull(message = "Last name is required")
    @Size(max = 50, message = "Last name must be less than 50 characters")
    private String lastName;

    @NotNull(message = "Username is required")
    @Size(max = 50, message = "Username must be less than 50 characters")
    private String username;

    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    private String picturePath;
    private Integer profileViews;
}
