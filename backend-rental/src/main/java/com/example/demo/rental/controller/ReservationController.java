package com.example.demo.rental.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.rental.model.dto.ApiResponse;
import com.example.demo.rental.model.dto.reservation.ReservationCreateRequest;
import com.example.demo.rental.model.dto.reservation.ReservationResponse;
import com.example.demo.rental.service.ReservationService;

import jakarta.validation.Valid;

/*
 * 預約功能 Controller
 * 
 * 負責:
 * 1.一般使用者
 * 2.查詢自己的預約
 * 3.取消自己的預約
 * 4.管理者查詢預約
 * 5.管理者核准預約
 * 6.管理者退回預約
 * 
 * */
@RestController
@RequestMapping("/api")
public class ReservationController {

	// 注入預約服務
	@Autowired
	private ReservationService reservationService;
	
	/*
	 * 建立預約
	 * 範例:
	 * POST /api/reservations
	 * 
	 * Authentication 代表目前登入的使用者資訊
	 * */
	
	@PostMapping("/reservations")
	public ApiResponse<ReservationResponse> create(Authentication authentication,
			@Valid @RequestBody ReservationCreateRequest request){
		ReservationResponse reservation = reservationService.create(authentication.getName(), request);
				return ApiResponse.success("建立預約成功, 等待管理者審核", reservation);
	}
	
	
}
