package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class AdminService {

	@Autowired
	private UserRepository userRepository;
	
	// 停權 / 啟用切換
	public String toggleUserStatus(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("找不到該使用者"));
		
		user.setEnabled(!user.isEnabled());
		userRepository.save(user);
		return user.isEnabled() ? "帳號已啟用" : "帳號已停權";
	}
	
	// 金流審核 : 將學生 paid 改為 true
	public String approvePayment(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("找不到該使用者"));
		
		if (user.isPaid()) {
			throw new IllegalArgumentException("該使用者已完成繳費審核");
		}
		
		user.setPaid(true);
		userRepository.save(user);
		return "繳費審核通過";
	}
	
	
	
}
