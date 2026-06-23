package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

	private Long id;
	private String username;
	private String email;
	private String role;
	private boolean paid;
	private boolean enabled;
}
