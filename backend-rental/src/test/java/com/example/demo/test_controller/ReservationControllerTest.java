package com.example.demo.test_controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.demo.rental.model.dto.reservation.ReservationCreateRequest;
import com.example.demo.rental.service.RentalItemService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private RentalItemService rentalItemService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	//@Test
	public void createReservation() throws Exception {
		String loginJson = """
				{
				    "username": "user",
				    "password": "user123"
				}
				""".trim();
		
		MvcResult loginResult = mockMvc.perform(post("/api/auth/login")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(loginJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		
		// 從登入回應的 JSON 中取出 token
		String responseBody = loginResult.getResponse().getContentAsString();
		
		// json 字串轉 json 物件
		JsonNode root = objectMapper.readTree(responseBody);
		System.out.println("json root: " + root);
		
		// JWT Token
		String token = root.path("data").path("token").asText();
		System.out.println("token: " + token);
		
		
		ReservationCreateRequest request = new ReservationCreateRequest();
		request.setItemId(5L);
		request.setStartTime(LocalDateTime.now().plusDays(1));
		request.setEndTime(LocalDateTime.now().plusDays(3));
		request.setNote("測試用1");
		
		MvcResult result = mockMvc.perform(post("/api/reservations")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		
		System.out.println(result);
		
	}
	
	//@Test
	@WithMockUser(username = "user", roles = {"USER"})
	public void createReservation2() throws Exception {
		
		ReservationCreateRequest request = new ReservationCreateRequest();
		request.setItemId(4L);
		request.setStartTime(LocalDateTime.now().plusDays(1));
		request.setEndTime(LocalDateTime.now().plusDays(3));
		request.setNote("測試用2");
		
		MvcResult result = mockMvc.perform(post("/api/reservations")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		
		System.out.println(result);
		
	}
	
	//@Test
	@WithMockUser(username = "user" , roles = {"USER"})
	public void findMine() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/reservations/my"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		System.out.println(result);
	}
	
	
	//@Test
	@WithMockUser(username = "user", roles = {"USER"})
	public void cancel() throws Exception {
		MvcResult result = mockMvc.perform(patch("/api/reservations/{id}/cancel", 7L))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		System.out.println(result);
	}
	
	//@Test
	@WithMockUser(username = "admin" , roles = {"ADMIN"})
	public void findAll() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/admin/reservations"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		System.out.println(result);
	}
	
	//@Test
	@WithMockUser(username = "admin" , roles = {"ADMIN"})
	public void approve() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/admin/reservations/{id}/approve" , 6L))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		System.out.println(result);
	}
	
	@Test
	@WithMockUser(username = "admin" , roles = {"ADMIN"})
	public void reject() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/admin/reservations/{id}/reject" , 3L))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		System.out.println(result);
	}
	
}