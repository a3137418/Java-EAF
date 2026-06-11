package com.example.demo.test_service;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.rental.model.dto.reservation.ReservationCreateRequest;
import com.example.demo.rental.model.dto.reservation.ReservationResponse;
import com.example.demo.rental.service.ReservationService;

@SpringBootTest
public class Test_Reservation {

	@Autowired
	private ReservationService reservationService;
	
	@Test
	public void create() {
		String username = "admin";
		
		
		LocalDateTime starTime = LocalDateTime.now()
				.plusDays(1);
		LocalDateTime endTime = LocalDateTime.now()
				.plusDays(2);
		
		ReservationCreateRequest request = new ReservationCreateRequest();
		request.setItemId(2L);
		request.setStartTime(starTime);
		request.setEndTime(endTime);
		request.setNote("測試測試");
		try {
			var response = reservationService.create(username, request);	
			System.out.println("預約成功");
		} catch (Exception e) {
			System.err.println(e);
		}
		
		System.out.println();
	}
	
	@Test
	public void findMine() {
		List<ReservationResponse> list = reservationService.findMine("admin");
		System.out.println("筆數: " + list.size());
		list.forEach(System.out::println);
	}
	
	@Test
	public void cancelMine() {
		try {
			reservationService.cancelMine("user", 1L);
			System.out.println("取消成功");
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	@Test
	public void approve() {
		try {
			reservationService.approve(5L);
			System.out.println("核准成功");
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	@Test
	public void reject() {
		try {
			reservationService.reject(4L);
			System.out.println("退回成功");
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
}
