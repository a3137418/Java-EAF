package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.AdminService;

@RestController
@RequestMapping("/api/users")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	// 停權 / 啟用
	@PutMapping("/{id}/status")
	@ResponseBody
	public String toggleStatus(@PathVariable Long id) {
		return adminService.toggleUserStatus(id);
	}

	// 金流審核
	@PostMapping("/{id}/approve")
	@ResponseBody
	public String approvePayment(@PathVariable Long id) {
		return adminService.approvePayment(id);
	}
}
