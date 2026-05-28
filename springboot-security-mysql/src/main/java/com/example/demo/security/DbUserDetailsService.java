package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;

import org.springframework.stereotype.Service;

import com.example.demo.entity.AppUser;
import com.example.demo.repository.AppUserRepository;

/**
 * 自訂: UserDetailsService
 * 功能: 讓 Spring Security 從 MySQL 查詢使用者
 * */
@Service
public class DbUserDetailsService implements UserDetailsService {
	
	
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser appUser = appUserRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("找不到使用者: " + username));
		
		return User.builder()
				.username(appUser.getUsername())
				.password(appUser.getPassword())
				.roles(appUser.getRole()) // 資料庫會存放 USER, 這裡會自動轉成 ROLE_USER
				.disabled(!appUser.isEnabled())
				.build();
	}

}