package com.example.demo.test_service;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.rental.exception.ResourceNotFoundException;
import com.example.demo.rental.model.dto.item.RentalItemRequest;
import com.example.demo.rental.model.enums.ItemStatus;
import com.example.demo.rental.service.RentalItemService;


@SpringBootTest
public class TestRentalItem {
	
	@Autowired
	private RentalItemService rentalItemService;
	
	@Test
	public void find() {
		String keyword = "null"; // "電腦", "4k"
		String type = "null"; // "教室", "設備" , "場地"
		var list = rentalItemService.findAll(keyword, type);
		
		System.out.println("筆數: " + list.size());
		list.forEach(System.out::println);
	}
	
	@Test
	public void findById() {
		Long id = 1L;
		try {
			var rentalItemResponse = rentalItemService.findById(id);
			System.out.println(rentalItemResponse);
		} catch (ResourceNotFoundException e) {
			System.err.println(e);
		}catch(Exception e) {
			System.err.println(e);
		}
	}
	
	
	@Test
	public void create() {
		RentalItemRequest request = new RentalItemRequest();
		
		try {
			request.setName("椅子");
			request.setStatus(ItemStatus.AVAILABLE);
			request.setType("家具");
			request.setLocation("倉庫");
			request.setPricePerHour(new BigDecimal("5"));
			request.setDescription("人體工學");
			request.setImageUrl("https://enjoycaster.com.tw/wp-content/uploads/2021/12/%E6%A4%8D%E5%B9%B8%E7%A6%8F-%E6%AD%A3-%E5%8E%BB%E8%83%8C-%E9%BB%91-600x900.jpg");
			
			var rentalItemResponse = rentalItemService.create(request);
			System.out.println("新增成功: " + rentalItemResponse);
		}catch (ResourceNotFoundException e) {
			System.err.println(e);
		}catch(Exception e) {
			System.err.println(e);
		}
		
		
	}
	

	@Test
	public void update() {
		try {
			RentalItemRequest request = new RentalItemRequest();
			request.setName("椅子2");
			request.setStatus(ItemStatus.MAINTENANCE);
			request.setType("家具2");
			request.setLocation("倉庫2");
			request.setPricePerHour(new BigDecimal("2"));
			request.setDescription("人體工學2");
			request.setImageUrl("");
			
			var rentalItemResponse = rentalItemService.update(6L , request);
			System.out.println("修改成功: " + rentalItemResponse);
		} catch (ResourceNotFoundException e) {
			System.err.println(e);
		}catch(Exception e) {
			System.err.println(e);
		}
		
		
	}
	
	@Test
	public void delete() {
		try {
			Long id = 6L;
			rentalItemService.delete(id);
			System.out.println("刪除成功");
		} catch (ResourceNotFoundException e) {
			System.err.println(e);
		}catch(Exception e) {
			System.err.println(e);
		}
		
	}
	
}