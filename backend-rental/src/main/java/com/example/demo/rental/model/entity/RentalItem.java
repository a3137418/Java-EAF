package com.example.demo.rental.model.entity;

import java.math.BigDecimal;

import com.example.demo.rental.model.enums.ItemStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rental_item")
@Getter
@Setter
@NoArgsConstructor
public class RentalItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false , length = 100)
	private String name;
	
	@Column(nullable = false , length = 40)
	private String type;
	
	@Column(nullable = false , length = 120)
	private String location;
	
	@Column(name = "price_per_hour" , nullable = false , precision = 10 , scale = 2)
	private BigDecimal pricePerHour;
	
	@Column(length = 500)
	private String description;
	
	@Column(name = "image_url" , length = 500)
	private String imageUrl;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false , length = 30)
	private ItemStatus status = ItemStatus.AVAILABLE;
}
