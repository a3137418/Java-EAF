package com.example.demo.rental.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.demo.rental.model.enums.ReservationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private AppUser user;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "item_id", nullable = false)
	private RentalItem item;
	
	@Column(name = "start_time" , nullable = false)
	private LocalDateTime startTime;
	
	@Column(name = "end_time" , nullable = false)
	private LocalDateTime endTime;
	
	@Column(name = "total_amount" , nullable = false , precision = 10 , scale = 2)
	private BigDecimal totalAmount;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false , length = 30)
	private ReservationStatus status = ReservationStatus.PENDING;
	
	@Column(length = 500)
	private String note;
	
	@CreationTimestamp
	@Column(name = "create_at", updatable = false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "update_at")
	private LocalDateTime updateAt;
}
