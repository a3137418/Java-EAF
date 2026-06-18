package com.example.demo.rental.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.rental.model.dto.ApiResponse;
import com.example.demo.rental.model.dto.auth.LoginRequest;
import com.example.demo.rental.model.dto.auth.LoginResponse;
import com.example.demo.rental.model.dto.auth.RegisterRequest;
import com.example.demo.rental.model.dto.auth.UserProfileDto;
import com.example.demo.rental.repository.RentalItemRepository;
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

    private final RentalItemRepository rentalItemRepository;

	@Autowired
	private AuthService authService;

    AuthController(RentalItemRepository rentalItemRepository) {
        this.rentalItemRepository = rentalItemRepository;
    }
	
	/*
	 * 使用者註冊
	 * URL: POST /api/auth/register
	 * */
	@PostMapping("/register")
	public ApiResponse<UserProfileDto> register(@Valid @RequestBody RegisterRequest request){
		UserProfileDto userProfileDto = authService.register(request);
		return ApiResponse.created("註冊成功", userProfileDto);
	}
	
	/*
	 * 使用者登入
	 * URL: POST /api/auth/login
	 * */
	@PostMapping("/login")
	public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request){
		LoginResponse response = authService.login(request);
		return ApiResponse.success("登入成功", response);
	}
	
	/*
	 * 查詢目前登入者的資料
	 * 
	 * URL: GET /api/auth/me
	 * 
	 * Authentication
	 * Spring Security 會根據 JWT 或 登入狀態，放入目前登入者的身分資訊
	 * */
	@GetMapping("/me")
	public ApiResponse<UserProfileDto> me(Authentication authentication){
		UserProfileDto userProfileDto = authService.getProfileDto(authentication.getName());
		return ApiResponse.success("查詢目前登入者成功", userProfileDto);
	}
}
