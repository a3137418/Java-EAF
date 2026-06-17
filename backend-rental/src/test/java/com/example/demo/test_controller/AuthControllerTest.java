package com.example.demo.test_controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;



import com.example.demo.rental.model.dto.auth.RegisterRequest;
import com.example.demo.rental.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // 先關掉 Spring Security Filter , 專心測試 Controller
public class AuthControllerTest {

	/*
	 * MockMVC 是用來模擬 HTTP Request
	 * 不需要真的啟動 Tomcat , 也可以測試 Controller API
	 * */
	
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	//@Transactional // 測試結束之後會自動 rollback , 避免每次測試都真的新增一筆資料到資料庫
	@Test
	public void register() throws Exception {
		String json = """
				{
					"username": "user4",
					"password": "444444",
					"fullName": "陳曉明",
					"phone": "0944444444"
				}
				""";
		
		mockMvc.perform(post("/api/auth/register")
				.with(csrf()) // 防止跨站請求偽造
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(jsonPath("$.message").value("註冊成功"));
		// 驗證 AuthService.register() 真的有被呼叫到一次
		//verify(authService, times(1)).register((RegisterRequest) any(RegisterRequest.class));
	}
	
	

	
	
}
