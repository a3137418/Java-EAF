package com.example.demo.service;
import com.example.demo.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Course;
import com.example.demo.model.Student;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	public StudentRepository studentRepository;
	
	@Autowired
	public CourseRepository courseRepository;

	
	// 新增學生
	public Student saveStudent(Student student) {
		return studentRepository.save(student);
	}
	
	// 查詢所有學生
	public List<Student> findAllStudents() {
		return studentRepository.findAll();
	}
	
	// 學生選課
	public String enrollCourse(Long studentId , Long courseId) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new RuntimeException("Student not found"));
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new RuntimeException("Course not found"));
		student.getCourses().add(course);
		studentRepository.save(student);
		
		return "選課成功";
		
	}
}
