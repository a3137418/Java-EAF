package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Teacher;
import com.example.demo.service.TeacherService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/teachers")
@CrossOrigin(origins = "*")
public class TeacherController {
	
	@Autowired
	private TeacherService teacherService;
	
	// 新增講師
	@PostMapping
	@ResponseBody
	public Teacher create(@Valid @RequestBody Teacher teacher) {
		return teacherService.saveTeacher(teacher);
	}
	
	// 查詢講師
	@GetMapping
	@ResponseBody
	public List<Teacher> getAll(){
		return teacherService.findAllTeachers();
	}
	
	// 修改講師
	@PutMapping("/{id}")
	@ResponseBody
	public Teacher update(@PathVariable Long id, @Valid @RequestBody Teacher teacher) {
		return teacherService.updateTeacher(id, teacher);
	}
	
	// 刪除講師
	@DeleteMapping("/{id}")
	@ResponseBody
	public String delete(@PathVariable Long id) {
		teacherService.deleteTeacher(id);
		return "刪除成功";
	}
	
	// 查詢名下課程的所有選修學生
	@GetMapping("/{id}/students")
	@ResponseBody
	public List<UserDTO> getEnrolledStudents(@PathVariable Long id){
		List<UserDTO> result = teacherService.getEnrolledStudents(id).stream().map(uer -> UserMapper.toProfileDTO(uer)).collect(Collectors.toList());
		return result;
	}
	
	
}
