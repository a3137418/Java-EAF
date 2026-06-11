package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;

	// 註冊
	@PostMapping("/register")
	@ResponseBody
	public ResponseEntity<?> register(@RequestBody User user){
		if(userRepository.existsByUsername(user.getUsername())) {
			return ResponseEntity.badRequest().body(Map.of("error" , "帳號已存在"));
		}
		
		if(userRepository.existsByEmail(user.getEmail())) {
			return ResponseEntity.badRequest().body(Map.of("error" , "Email 已被使用"));
		}
		
		// 密碼加密
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		// 若未指定角色，預設為學生
		if(user.getRole() == null || user.getRole().isBlank()) {
			user.setRole("ROLE_STUDENT");
		}
		user.setPaid(false);
		user.setEnabled(true);
		userRepository.save(user);
		
		return ResponseEntity.ok(Map.of("message" , "註冊成功"));		
	}
	
	// 登入，回傳 JWT Token
	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<?> login(@RequestBody Map<String , String> body){
		String username = body.get("username");
		String password = body.get("password");
		
		User user = userRepository.findByUsername(username).orElse(null);
		
		if(user == null || !passwordEncoder.matches(password, user.getPassword())) {
			return ResponseEntity.status(401).body(Map.of("error" , "帳號或密碼錯誤"));
		}
		
		if(!user.isEnabled()) {
			return ResponseEntity.status(403).body(Map.of("error" , "帳號已停權"));
		}
		
		try {
			String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
			return ResponseEntity.ok(Map.of("token" , token , "role" , user.getRole()));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(Map.of("error" , "Token 產生失敗"));
		}
		
	}
	
	
	
}
