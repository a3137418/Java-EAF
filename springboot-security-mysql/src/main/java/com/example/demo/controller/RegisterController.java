package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.service.RegisterService;

// 會員註冊 Controller
@Controller
public class RegisterController {
	
	@Autowired
	private RegisterService registerService;
	
	@GetMapping("/register")
	public String registerPage() {
		return "register";
	}
	
	@PostMapping("/register")
	public String register(String username, String password) {
		try {
			registerService.register(username, password);
			return "redirect:/login?registerSuccess=true";
		} catch (IllegalArgumentException e) {
			return "redirect:/login?registerError=true";
		}
		
	}
	
}