package com.example.demo.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	
	@GetMapping("/welcome")
	public String welcome(Model model) {
		//model 裡面放的就是要傳給JSP的資料
		model.addAttribute("name" , "阿山哥");
		model.addAttribute("now" , new Date());
		
		return "welcome"; // 取welcome.jsp檔名的部分
	}

}
