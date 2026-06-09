package com.example.demo.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;

import com.example.demo.model.Course;
import com.example.demo.service.CourseService;

@Controller
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {

	@Autowired
	private CourseService courseService;

	
	// 新增課程(指定講師id)
	@PostMapping
	@ResponseBody
	public Course create(@RequestParam Long teacherId, @Valid @RequestBody Course course) {
		return courseService.saveCourse(teacherId, course);
	}
	
	// 查詢所有課程
	@GetMapping
	@ResponseBody
	public List<Course> getAll(){
		return courseService.findAllCourses();
	}
	
	// 修改課程
	@PutMapping("/{id}")
	@ResponseBody
	public Course update(@PathVariable Long id, @Valid @RequestBody Course course) {
		return courseService.updateCourse(id, course);
	}
	
	// 刪除課程
	@DeleteMapping("/{id}")
	@ResponseBody
	public String delete(@PathVariable Long id) {
		courseService.deleteCourse(id);
		return "刪除成功";
		
	}
	
}
