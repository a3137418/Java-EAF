package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {

	@NotBlank(message = "帳號不可空白")
	private String username;
	
	@NotBlank(message = "密碼不可空白")
	private String password;
}
