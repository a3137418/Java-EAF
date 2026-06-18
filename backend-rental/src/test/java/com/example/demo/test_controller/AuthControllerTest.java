package com.example.demo.test_controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.demo.rental.service.AuthService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureMockMvc
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
	//@Test
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
	
	
	//@Test
	public void login() throws Exception {
		String loginJson = """
				{
					"username": "admin",
					"password": "admin123"
				}
				""".trim();
		mockMvc.perform(post("/api/auth/login")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(loginJson))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	
	
	@Test
	public void me() throws Exception {
		String loginJson = """
				{
					"username": "admin",
					"password": "admin123"
				}
				""".trim();
		MvcResult loginMvcResult = mockMvc.perform(post("/api/auth/login")
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
					.content(loginJson))
					.andDo(print())
					.andExpect(status().isOk())
					.andReturn();
		
		// 從登入回應的 JSON 中取出 Token
		String responseBody = loginMvcResult.getResponse().getContentAsString();
		
		// json 字串轉 json 物件
		JsonNode root = objectMapper.readTree(responseBody);
		System.out.println("json root: " + root);
		
		// JWT Token
		String token = root.path("data").path("token").asText();
		System.out.println("token: " + token);
		
		// 放錯誤的 Token
		//token = "1234";
		
		
		// 帶著 JWT Token 呼叫 "/api/auth/me"
		mockMvc.perform(get("/api/auth/me")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
				.andDo(print())
				.andExpect(status().isOk());
		
	}
	
	
	
	
	
}
