package com.example.demo.test_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.rental.model.dto.auth.RegisterRequest;
import com.example.demo.rental.service.AuthService;

@SpringBootTest
public class TestAuth {

	@Autowired
	private AuthService authService;
	
	@Test
	public void register() {
		RegisterRequest request = new RegisterRequest();
		request.setUsername("user2");
		request.setFullName("李曉玫");
		request.setPassword("2222");
		request.setPhone("0922222222");
		
		try {
			authService.register(request);
			System.out.println("註冊成功");
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}
	
	
}
