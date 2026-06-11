package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Course;
import com.example.demo.model.User;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.model.Teacher;



@Service
public class TeacherService {

	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private CourseRepository courseRepository;
	
	// 新增講師
	public Teacher saveTeacher(Teacher teacher) {
		if (teacherRepository.existsByName(teacher.getName())) {
			throw new IllegalArgumentException("講師「" + teacher.getName() + "」已存在，不可重複新增");
		}
		return teacherRepository.save(teacher);
	}
	
	// 查詢所有講師
	public List<Teacher> findAllTeachers(){
		return teacherRepository.findAll();
	}
	
	// 修改講師
	public Teacher updateTeacher(Long id, Teacher updated) {
		Teacher existing = teacherRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("找不到該講師"));
		if (teacherRepository.existsByNameAndIdNot(updated.getName(), id)) {
			throw new IllegalArgumentException("講師名稱「" + updated.getName() + "」已被其他講師使用");
		}
		existing.setName(updated.getName());
		existing.setExpertise(updated.getExpertise());
		return teacherRepository.save(existing);
	}
	
	// 刪除講師
	public void deleteTeacher(Long id) {
		teacherRepository.deleteById(id);
	}

	// 查詢講師名下所有選修學生
	@Transactional(readOnly = true)
	public List<User> getEnrolledStudents(Long teacherId) {
		teacherRepository.findById(teacherId)
				.orElseThrow(() -> new ResourceNotFoundException("找不到該講師"));

		List<Course> courses = courseRepository.findByTeacherId(teacherId);
		return courses.stream()
				.flatMap(c -> c.getEnrolledUsers().stream())
				.distinct()
				.collect(Collectors.toList());
	}
	
	
}
