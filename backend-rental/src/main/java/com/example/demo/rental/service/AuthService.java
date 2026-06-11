package com.example.demo.rental.service;
import com.example.demo.rental.repository.RentalItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.rental.exception.BusinessException;
import com.example.demo.rental.mapper.UserMapper;
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
	
	
	
}
