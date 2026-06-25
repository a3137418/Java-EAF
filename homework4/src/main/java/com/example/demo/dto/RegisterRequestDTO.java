package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDTO {
	
	@NotBlank(message = "帳號不可空白")
	@Size(min = 3 , max = 50 , message = "帳號長度必須介於3到50")
	private String username;
	
	@NotBlank(message = "密碼不可空白")
	@Size(min = 6 , max = 100 , message = "密碼長度至少6位數")
	private String password;
	
	@NotBlank(message = "email不可空白")
	private String email;
	
}
