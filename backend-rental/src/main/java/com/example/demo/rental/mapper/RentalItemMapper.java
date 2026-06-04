package com.example.demo.rental.mapper;

import com.example.demo.rental.model.dto.item.RentalItemRequest;
import com.example.demo.rental.model.dto.item.RentalItemResponse;
import com.example.demo.rental.model.entity.RentalItem;

public class RentalItemMapper {
	
	// entity:RentalItem 轉 dto:RentalItemResponse
	public static RentalItemResponse toResponse(RentalItem item) {
		return new RentalItemResponse(
				item.getId(), 
				item.getName(), 
				item.getType(), 
				item.getLocation(), 
				item.getPricePerHour(), 
				item.getStatus().name(), 
				item.getImageUrl()
		);
	}
	
	// 將 dto:RentalItemRequest 複製到 entity:RentalItem
	public static void copyToEntity(RentalItemRequest request, RentalItem item) {
		item.setName(request.getName());
		item.setType(request.getType());
		item.setLocation(request.getLocation());
		item.setPricePerHour(request.getPricePerHour());
		item.setStatus(request.getStatus());
		item.setDescription(request.getDescription());
		item.setImageUrl(request.getImageUrl());
	}
	
}