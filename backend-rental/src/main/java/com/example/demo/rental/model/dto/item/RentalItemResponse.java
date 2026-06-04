package com.example.demo.rental.model.dto.item;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RentalItemResponse {

	private Long id;
	private String name;
	private String type;
	private String localtion;
	private BigDecimal pricePerHour;
	private String status;
	private String imageUrl;
}
