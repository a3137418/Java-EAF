package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	// 新增使用者（同時新增 UserProfile，因為有 cascade）
	public User saveUser(User user) {
		if (userRepository.existsByUsername(user.getUsername())) {
			throw new IllegalArgumentException("帳號「" + user.getUsername() + "」已存在，不可重複新增");
		}
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new IllegalArgumentException("Email「" + user.getEmail() + "」已被使用");
		}
		return userRepository.save(user);
	}
	
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

}
