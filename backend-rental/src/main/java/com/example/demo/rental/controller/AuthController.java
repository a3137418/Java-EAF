package com.example.demo.rental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.rental.model.dto.ApiResponse;
import com.example.demo.rental.model.dto.auth.RegisterRequest;
import com.example.demo.rental.model.dto.auth.UserProfileDto;
import com.example.demo.rental.service.AuthService;

import jakarta.validation.Valid;



/*
 * 負責:
 * 1.使用者註冊
 * 2.使用者登入
 * 3.查詢目前登入者資料
 * */

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	/*
	 * 使用者註冊
	 * URL: POST /api/auth/register
	 * */
	@PostMapping("/register")
	public ApiResponse<UserProfileDto> register(@Valid @RequestBody RegisterRequest request){
		UserProfileDto userProfileDto = authService.register(request);
		return ApiResponse.created("註冊成功", userProfileDto);
	}
	
}
