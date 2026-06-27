package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserService userService;

	// 新增使用者
	@PostMapping("/api/users")
	@ResponseBody
	public UserDTO create(@Valid @RequestBody User user) {
		return UserMapper.toProfileDTO(userService.saveUser(user));
	}

	// 查詢所有使用者
	@GetMapping("/api/users")
	@ResponseBody
	public List<UserDTO> findAll() {
		List<UserDTO> result = 
		userService
		// 撈出所有user
		.findAllUsers()
		// 將List轉成串流
		.stream()
		// 將每一筆資料做相同的處理 user 轉成userDTO
		.map(user -> UserMapper.toProfileDTO(user))
		// 收集成 List<UserDTO>
		.collect(Collectors.toList());
		return result;
	}

	// 刪除使用者（含 UserProfile）
	@DeleteMapping("/api/users/{id}")
	@ResponseBody
	public String delete(@PathVariable Long id) {
		userService.deleteUser(id);
		return "刪除成功(含 UserProfile)";
	}

	// 使用者（學生）選修課程，建立多對多關係
	@PostMapping("/api/students/{studentId}/enroll/{courseId}")
	@ResponseBody
	public String enroll(@PathVariable Long studentId, @PathVariable Long courseId) {
		return userService.enrollCourse(studentId, courseId);
	}
}
