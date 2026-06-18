package com.example.demo.rental.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.rental.model.dto.ApiResponse;
import com.example.demo.rental.model.dto.item.RentalItemRequest;
import com.example.demo.rental.model.dto.item.RentalItemResponse;
import com.example.demo.rental.service.RentalItemService;

import jakarta.validation.Valid;

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
	
	
	/*
	 * 2.查詢單一租用項目
	 * 範例:
	 * GET /api/items/{id}
	 * */
	@GetMapping("/items/{id}")
	public ApiResponse<RentalItemResponse> findById(@PathVariable Long id){
		RentalItemResponse rentalItem = rentalItemService.findById(id);
		return ApiResponse.success("查詢租用項目成功", rentalItem);
	}
	
	
	/*
	 * 3.管理者新增租用項目
	 * 範例:
	 * POST /api/admin/items
	 * */
	@PostMapping("/admin/items")
	@PreAuthorize("hasRole('ADMIN')") // 限制只有 ADMIN 角色可以新增
	public ApiResponse<RentalItemResponse> create(@Valid @RequestBody RentalItemRequest request){
		RentalItemResponse rentalItem = rentalItemService.create(request);
		return ApiResponse.created("新增租用項目成功", rentalItem);
	}
	
	/*
	 * 4.管理者修改租用項目
	 * 範例:
	 * PUT /api/items/{id}
	 * */
	@PutMapping("/admin/items/{id}")
	@PreAuthorize("hasRole('ADMIN')") // 限制只有 ADMIN 角色可以修改
	public ApiResponse<RentalItemResponse> update(@PathVariable Long id , @Valid @RequestBody RentalItemRequest request){
		RentalItemResponse rentalItem = rentalItemService.update(id , request);
		return ApiResponse.created("修改租用項目成功", rentalItem);
	}
	
	/*
	 * 5.管理者刪除租用項目
	 * 範例:
	 * DELETE /api/admin/items/{id}
	 * */
	@DeleteMapping("/admin/items/{id}")
	@PreAuthorize("hasRole('ADMIN')")// 限制只有 ADMIN 角色可以刪除
	public ApiResponse<RentalItemResponse> delete(@PathVariable Long id){
		 rentalItemService.delete(id);
	     return ApiResponse.success("刪除租用項目成功", null);
	}
	
}
