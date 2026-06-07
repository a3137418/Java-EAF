package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;

@Controller
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	// 新增學生
	@PostMapping
	@ResponseBody
	public Student create(@Valid @RequestBody Student student) {
		return studentService.saveStudent(student);
	}
	
	// 查詢所有學生
	@GetMapping
	@ResponseBody
	public List<Student> findAll(){
		return studentService.findAllStudents();
	}
	
	// 學生選課
	@PostMapping("/{studentId}/enroll/{courseId}")
	@ResponseBody
	public String enroll(@PathVariable Long studentId , @PathVariable Long courseId) {
		return studentService.enrollCourse(studentId, courseId);
	}
	
}
