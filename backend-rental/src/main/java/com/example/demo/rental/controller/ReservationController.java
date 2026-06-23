package com.example.demo.rental.controller;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	/**
	 * 查詢我的預約
	 * 範例: GET /api/reservations/my
	 * 
	 * */
	@GetMapping("/reservations/my")
	public ApiResponse<List<ReservationResponse>> findMine(Authentication authentication) {
		List<ReservationResponse> reservationResponses = reservationService.findMine(authentication.getName());
		return ApiResponse.success("查詢我的預約成功", reservationResponses);
	}
	
	/*
	 * 取消預約
	 * 範例: PATCH /api/reservations/{id}/cancel
	 * */
	
	@PatchMapping("/reservations/{id}/cancel")
	public ApiResponse<ReservationResponse> cancel(Authentication authentication , @PathVariable Long id){
		ReservationResponse reservationResponse = reservationService.cancelMine(authentication.getName(), id);
		return ApiResponse.success("取消預約成功", reservationResponse);
	}
	
	/*
	 * 查詢所有預約
	 * 範例: GET /api/admin/reservations
	 * */
	
	@GetMapping("/admin/reservations")	
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse<List<ReservationResponse>> findAll(){
		List<ReservationResponse> reservationResponses = reservationService.findAll();
		return ApiResponse.success("查詢所有預約成功", reservationResponses);
	}
	
	/*
	 * 核准預約
	 * 範例: GET /api/admin/reservations/{id}/approve
	 * */
	@GetMapping("/admin/reservations/{id}/approve")
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse<ReservationResponse> approve(@PathVariable Long id) {
		ReservationResponse reservationResponse = reservationService.approve(id);
		return ApiResponse.success("核准預約成功", reservationResponse);
	}
	
	/*
	 * 退回預約
	 * 範例: GET /api/admin/reservations/{id}/reject
	 * */
	@GetMapping("/admin/reservations/{id}/reject")
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse<ReservationResponse> reject(@PathVariable Long id) {
		ReservationResponse reservationResponse = reservationService.reject(id);
		return ApiResponse.success("退回預約成功", reservationResponse);
	}
	
}
