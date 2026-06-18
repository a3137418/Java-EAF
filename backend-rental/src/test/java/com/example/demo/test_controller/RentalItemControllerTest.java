package com.example.demo.test_controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.demo.rental.model.dto.item.RentalItemRequest;
import com.example.demo.rental.model.enums.ItemStatus;
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
	
	//@Test
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
	
	//@Test
	public void findById() throws Exception{
		MvcResult result = mockMvc.perform(get("/api/items/{id}" , 1L))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		String responseBody = result.getResponse().getContentAsString();
		System.out.println(responseBody);
	}
	
	//@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	public void createByAdmin() throws Exception {
		
		RentalItemRequest request = new RentalItemRequest();
		request.setName("投影機");
		request.setStatus(ItemStatus.AVAILABLE);
		request.setType("設備");
		request.setLocation("倉庫3");
		request.setPricePerHour(new BigDecimal("50"));
		request.setDescription("放大到200吋");
		
		MvcResult result = mockMvc.perform(post("/api/admin/items")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		
		System.out.println(result);
	}
	
	
	//@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	public void updateByAdmin() throws Exception{
		RentalItemRequest request = new RentalItemRequest();
		request.setName("4K投影機");
		request.setStatus(ItemStatus.AVAILABLE);
		request.setType("設備");
		request.setLocation("倉庫4");
		request.setPricePerHour(new BigDecimal("500"));
		request.setDescription("放大到300吋");
		
		MvcResult result = mockMvc.perform(put("/api/admin/items/{id}" , 7L)
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		System.out.println(result);
	}
	
	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	public void deleteByAdmin() throws Exception{
		MvcResult result = mockMvc.perform(delete("/api/admin/items/{id}" , 5L)
				.with(csrf()))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		System.out.println(result);
	}
	
	
}
