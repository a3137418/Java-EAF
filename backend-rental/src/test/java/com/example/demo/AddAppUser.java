package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.rental.model.entity.AppUser;
import com.example.demo.rental.model.enums.Role;
import com.example.demo.rental.repository.AppUserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class AddAppUser {
	
	@Autowired
	private AppUserRepository userRepository;
	
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Test
	public void add() {
		// 新增加 admin 與 user
		if(!userRepository.existsByUsername("admin")) {
			AppUser admin = new AppUser();
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder().encode("admin123"));
			admin.setFullName("系統管理員");
			admin.setPhone("0900000000");
			admin.setRole(Role.ADMIN);
			admin.setEnabled(true);
			
			userRepository.save(admin);
			System.out.println("admin add OK");
		} else {
			System.out.println("admin 已存在");
		}
		
		if(!userRepository.existsByUsername("user")) {
			AppUser user = new AppUser();
			user.setUsername("user");
			user.setPassword(passwordEncoder().encode("user123"));
			user.setFullName("王小名");
			user.setPhone("0912345678");
			user.setRole(Role.USER);
			user.setEnabled(true);
			
			userRepository.save(user);
			System.out.println("user add OK");
		} else {
			System.out.println("user 已存在");
		}
		
	}
	
}