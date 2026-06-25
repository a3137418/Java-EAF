package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	// 註冊
	@PostMapping("/register")
	public ApiResponse<UserDTO> register(@Valid @RequestBody RegisterRequestDTO request){
		UserDTO userDTO = authService.register(request);
		return ApiResponse.success("註冊成功", userDTO);
		
	}

	// 登入
	@PostMapping("/login")
	public ApiResponse<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request){
		LoginResponseDTO loginResponseDTO = authService.login(request);
		return ApiResponse.success("登入成功", loginResponseDTO);
	}
	
	
	
}
