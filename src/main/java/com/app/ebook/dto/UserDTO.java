package com.app.ebook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long userId;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
    private String picturePath;
    private Integer profileViews;
}
