package com.example.demo.rental.model.dto.auth;

import lombok.Data;

@Data
public class UserProfileDto {

	private Long id;
	private String username;
	private String fullName;
	private String phone;
	private String role;
}
