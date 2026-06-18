package com.example.demo.test_controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.demo.rental.service.RentalItemService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class RentalItemControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private RentalItemService rentalItemService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void findAll() throws Exception {
		
		
		// 查詢全部
		/*
		MvcResult result = mockMvc.perform(get("/api/items"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		*/	
		
		// GET /api/items?keyword=攝影機
		/*
		MvcResult result = mockMvc.perform(get("/api/items").param("keyword", "攝影機"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		*/
		
		// GET /api/items?type=設備
		/*
		MvcResult result = mockMvc.perform(get("/api/items").param("type", "設備"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		*/
		
		
		
		MvcResult result = mockMvc.perform(get("/api/items").param("keyword", "攝影機").param("type", "設備"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		
		
		String responseBody = result.getResponse().getContentAsString();
		System.out.println(responseBody);
		
		
	}
	
}
