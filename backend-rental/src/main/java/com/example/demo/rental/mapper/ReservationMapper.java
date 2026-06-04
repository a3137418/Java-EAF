package com.example.demo.rental.mapper;

import com.example.demo.rental.model.dto.reservation.ReservationResponse;
import com.example.demo.rental.model.entity.Reservation;

public class ReservationMapper {
	
	public static ReservationResponse toResponse(Reservation r) {
		return new ReservationResponse(
				r.getId(), 
				r.getUser().getId(),
				r.getUser().getUsername(), 
				r.getUser().getFullName(),
				r.getItem().getId(),
				r.getItem().getName(),
				r.getItem().getType(),
				r.getStartTime(),
				r.getEndTime(),
				r.getTotalAmount(),
				r.getStatus().name(),
				r.getNote(),
				r.getCreatedAt());
	}
	
}