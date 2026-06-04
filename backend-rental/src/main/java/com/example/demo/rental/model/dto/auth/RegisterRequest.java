package com.example.demo.rental.model.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

	@NotBlank(message = "帳號不可空白")
	@Size(min = 3 , max = 50 , message = "帳號長度必須介於 3 到 50 ")
	private String username;
	
	@NotBlank(message = "密碼不可空白")
	@Size(min = 6 , max = 100 , message = "密碼長度至少 6 個字")
	private String password;
	
	@NotBlank(message = "姓名不可空白")
	private String fullName;
	
	private String phone;
}
