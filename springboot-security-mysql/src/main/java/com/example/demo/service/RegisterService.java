package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.AppUser;
import com.example.demo.repository.AppUserRepository;

// 註冊服務
@Service
public class RegisterService {
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public void register(String username, String password) {
		if(username == null || username.isBlank() || username.isEmpty()) {
			throw new IllegalArgumentException("帳號不可空白");
		}
		
		if(password == null || password.isBlank() || password.isEmpty()) {
			throw new IllegalArgumentException("密碼不可空白");
		}
		
		if(appUserRepository.existsByUsername(username)) {
			throw new IllegalArgumentException("帳號已存在");
		}
		
		AppUser appUser = new AppUser();
		appUser.setUsername(username);
		appUser.setPassword(passwordEncoder.encode(password));
		appUser.setRole("USER");
		appUser.setEnabled(true);
		
		appUserRepository.save(appUser);
	}
	
}