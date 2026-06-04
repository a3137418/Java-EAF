package com.example.demo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.rental.model.dto.reservation.ReservationResponse;
import com.example.demo.rental.model.entity.AppUser;
import com.example.demo.rental.model.entity.RentalItem;
import com.example.demo.rental.model.entity.Reservation;
import com.example.demo.rental.model.enums.ReservationStatus;
import com.example.demo.rental.repository.AppUserRepository;
import com.example.demo.rental.repository.RentalItemRepository;
import com.example.demo.rental.repository.ReservationRepository;

@SpringBootTest
public class AddReservation {

	@Autowired
	 private AppUserRepository userRepository;
	
	@Autowired
	private RentalItemRepository itemRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Test
	public void add() {
		AppUser user = userRepository.findByUsername("user").get();
		RentalItem item = itemRepository.findById(1L).get();
		
		LocalDateTime startTime = LocalDateTime.of(2026, 6, 10, 19, 0);
		LocalDateTime endTime = LocalDateTime.of(2026, 6, 12, 21, 0);
		Reservation reservation = saveReservation(user, item, startTime, endTime, ReservationStatus.PENDING);
		
		System.out.println("租借狀態: " + reservation.getStatus().name());
	}
	
	private Reservation saveReservation(
			AppUser user,
			RentalItem item,
			LocalDateTime startTime,
			LocalDateTime endTime,
			ReservationStatus status) {
		
		Reservation reservation = new Reservation();
		reservation.setUser(user);
		reservation.setItem(item);
		reservation.setStartTime(startTime);
		reservation.setEndTime(endTime);
		reservation.setTotalAmount(new BigDecimal("1000.00"));
		reservation.setStatus(status);
		reservation.setNote("預約測試");
		
		return reservationRepository.save(reservation);
	}
}
