package com.example.demo.test_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.rental.model.dto.auth.LoginRequest;
import com.example.demo.rental.model.dto.auth.LoginResponse;
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
	
	/*
	 * admin/admin123
	 * user/user123
	 * user2/2222
	 * */
	@Test
	public void login() {
		LoginRequest login = new LoginRequest();
		login.setUsername("user");
		login.setPassword("user123");
		
		try {
			LoginResponse reponse = authService.login(login);
			System.out.println("登入成功");
			System.out.println("token type: " +reponse.getTokenType());
			System.out.println("token: " +reponse.getToken());
			System.out.println("user: " +reponse.getUser());
			System.out.println("fullname: " +reponse.getUser().getFullName());
			System.out.println("username: " +reponse.getUser().getUsername());
			System.out.println("role: " +reponse.getUser().getRole());
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	
	
	
}
