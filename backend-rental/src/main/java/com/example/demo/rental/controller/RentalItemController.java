package com.example.demo.rental.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.rental.model.dto.ApiResponse;
import com.example.demo.rental.model.dto.item.RentalItemResponse;
import com.example.demo.rental.service.RentalItemService;

/*
 * 租用項目 Controller
 * 
 * 負責:
 * 1.查詢可租用項目
 * 2.查詢單一租用項目
 * 3.管理者新增租用項目
 * 4.管理者修改租用項目
 * 5.管理者刪除租用項目
 * */


@RestController
@RequestMapping("/api")
public class RentalItemController {

	// 注入租用項目服務
	@Autowired
	private RentalItemService rentalItemService;
	
	/*
	 * 1.查詢所有可租用項目
	 * 範例:
	 * GET /api/items
	 * GET /api/items?keyword=攝影機
	 * GET /api/items?type=設備
	 * 
	 * */
	
	@GetMapping("/items")
	public ApiResponse<List<RentalItemResponse>> findAll(@RequestParam(required = false) String keyword,
														 @RequestParam(required = false) String type){
		List<RentalItemResponse> rentalItems = rentalItemService.findAll(keyword, type);
		return ApiResponse.success("查詢租用項目成功", rentalItems);
	}
	
	
	
}
