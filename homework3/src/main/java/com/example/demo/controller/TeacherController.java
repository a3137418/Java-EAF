package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Course;
import com.example.demo.model.Teacher;
import com.example.demo.model.User;
import com.example.demo.repository.CourseRepository;
import com.example.demo.service.TeacherService;

@Controller
@RequestMapping("/api/teachers")
@CrossOrigin(origins = "*")
public class TeacherController {
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private CourseRepository courseRepository;
	
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
	public List<User> getEnrolledStudents(@PathVariable Long id){
		
		// 先確認 講師存在
		teacherService.findAllTeachers().stream()
			.filter(t -> t.getId().equals(id))
			.findFirst()
			.orElseThrow(() -> new ResourceNotFoundException("找不到該講師"));
		
		
		List<Course> courses = courseRepository.findByTeacherId(id);
		return courses.stream()
				.flatMap(c -> c.getEnrolledUsers().stream())
				.distinct()
				.collect(Collectors.toList());
	}
	
	
}
