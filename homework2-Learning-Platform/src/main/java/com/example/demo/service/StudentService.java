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
		if (studentRepository.existsByName(student.getName())) {
			throw new IllegalArgumentException("學生「" + student.getName() + "」已存在，不可重複新增");
		}
		return studentRepository.save(student);
	}
	
	// 查詢所有學生
	public List<Student> findAllStudents() {
		return studentRepository.findAll();
	}
	
	// 學生選課
	public String enrollCourse(Long studentId, Long courseId) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new RuntimeException("找不到該學生"));
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new RuntimeException("找不到該課程"));
		boolean alreadyEnrolled = student.getCourses().stream()
				.anyMatch(c -> c.getId().equals(courseId));
		if (alreadyEnrolled) {
			throw new IllegalArgumentException("該學生已選修此課程，不可重複選課");
		}
		student.getCourses().add(course);
		studentRepository.save(student);
		return "選課成功";
	}
}
