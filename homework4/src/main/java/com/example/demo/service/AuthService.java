package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;


@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	// 註冊
	public UserDTO register(RegisterRequestDTO request) {
		if(userRepository.existsByUsername(request.getUsername())) {
			throw new IllegalArgumentException("帳號已存在");
		}
		
		if(userRepository.existsByEmail(request.getEmail())) {
			throw new IllegalArgumentException("email已存在");
		}
		
		User user = new User();
	
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole("ROLE_STUDENT");
		user.setPaid(false);
		user.setEnabled(true);
		userRepository.save(user);
		
		return UserMapper.toProfileDTO(user);
	}
	
	// 登入，回傳 JWT Token
	public LoginResponseDTO login(LoginRequestDTO request) {
		
		// 查詢使用者
		User user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new ResourceNotFoundException("找不到該使用者"));
		// 帳號或密碼錯誤檢查
		if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new IllegalArgumentException("帳號或密碼錯誤");
		}
		
		// 停權檢查
		if(!user.isEnabled()) {
			throw new IllegalArgumentException("帳號已停權");
		}
		
		// 產生token
		 try {
		      String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
		      return new LoginResponseDTO(token, "Bearer", UserMapper.toProfileDTO(user));
		  } catch (Exception e) {
		      throw new RuntimeException("Token 產生失敗");
		  }
		
		
	}
	
}
