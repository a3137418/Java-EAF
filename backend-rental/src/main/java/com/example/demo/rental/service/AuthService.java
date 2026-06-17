package com.example.demo.rental.service;
import com.example.demo.rental.security.JwtService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.rental.exception.BusinessException;
import com.example.demo.rental.exception.ResourceNotFoundException;
import com.example.demo.rental.mapper.UserMapper;
import com.example.demo.rental.model.dto.auth.LoginRequest;
import com.example.demo.rental.model.dto.auth.LoginResponse;
import com.example.demo.rental.model.dto.auth.RegisterRequest;
import com.example.demo.rental.model.dto.auth.UserProfileDto;
import com.example.demo.rental.model.entity.AppUser;
import com.example.demo.rental.model.enums.Role;
import com.example.demo.rental.repository.AppUserRepository;

/*
 * 認證與會員帳號服務層
 * 
 * 負責處理系統中 "會員註冊"，"登入驗證"，"JWT Token 簽發"，"個人資料查詢"
 *  
 *  架構:
 *  Controller ->AuthService -> Repository / Security Component -> Database
 * 
 * */

@Service
public class AuthService {

	@Autowired
	private AppUserRepository appUserRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	/*
	 * 會員註冊
	 * 
	 * */
	@Transactional
	public UserProfileDto register(RegisterRequest request) {
		if (appUserRepository.existsByUsername(request.getUsername())) {
			throw new BusinessException("帳號已存在");
		}
		
		AppUser user = new AppUser();
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setFullName(request.getFullName());
		user.setPhone(request.getPhone());
		user.setRole(Role.USER);
		user.setEnabled(true);
		
		user = appUserRepository.save(user);
		
		
		return UserMapper.toProfileDto(user);
	}
	
	/*
	 * 登入驗證
	 * 流程:
	 * 1.將前端輸入的帳號與密碼封裝成 UsernamePasswordAuthenticationToken
	 * 2.交由 AuthenticationManger 進行 Spring Security 標準認證
	 * 3.認證成功後重新查詢使用者資料
	 * 4.依據 username 與 role 產生 JWT Token
	 * 5.回傳 Token 類型，Token 字串與使用者基本資料
	 * 
	 * 注意:
	 * 此方法不直接進行比對密碼，而是交由 Spring Security 來管理
	 * 這樣就可以保持安全驗證流程一致
	 * */
	
	
	public LoginResponse login(LoginRequest request) {
		// 1.將前端輸入的帳號與密碼封裝成 UsernamePasswordAuthenticationToken
		Authentication auth = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
		
		// 2.交由 AuthenticationManger 進行 Spring Security 標準認證
		authenticationManager.authenticate(auth);
		
		// 3.認證成功後重新查詢使用者資料
		AppUser user = appUserRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new ResourceNotFoundException("找不到使用者"));
		
		// 4.依據 username 與 role 產生 JWT Token
		String token = jwtService.generateToken(user.getUsername(), user.getRole().name());
		
		// 5.回傳 Token 類型，Token 字串與使用者基本資料
		return new LoginResponse(token, "Bearer" , UserMapper.toProfileDto(user));
		
	}
	
	
	/*
	 * 查詢目前使用者的個人資料
	 * 系統會根據 username 查詢使用者資料並轉換 DTO 回傳給前端
	 * */
	public UserProfileDto getProfileDto(String username) {
		AppUser user = appUserRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("找不到使用者"));
		return UserMapper.toProfileDto(user);
	}

}
