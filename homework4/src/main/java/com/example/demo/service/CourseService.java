package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Course;
import com.example.demo.model.Teacher;
import com.example.demo.model.User;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.repository.UserRepository;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private UserRepository userRepository;

	// 新增課程(指定講師)
	public Course saveCourse(Long teacherId, Course course) {
		if (courseRepository.existsByTitle(course.getTitle())) {
			throw new IllegalArgumentException("課程「" + course.getTitle() + "」已存在，不可重複新增");
		}
		Teacher teacher = teacherRepository.findById(teacherId)
				.orElseThrow(() -> new ResourceNotFoundException("找不到該講師"));
		course.setTeacher(teacher);
		return courseRepository.save(course);
	}

	// 查詢所有課程
	public List<Course> findAllCourses() {
		return courseRepository.findAll();
	}

	// 修改課程（含講師）
	public Course updateCourse(Long id, Course update) {
		Course existing = courseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("找不到該課程"));
		if (courseRepository.existsByTitleAndIdNot(update.getTitle(), id)) {
			throw new IllegalArgumentException("課程名稱「" + update.getTitle() + "」已被其他課程使用");
		}
		existing.setTitle(update.getTitle());
		existing.setPrice(update.getPrice());
		if (update.getTeacher() != null && update.getTeacher().getId() != null) {
			Teacher teacher = teacherRepository.findById(update.getTeacher().getId())
					.orElseThrow(() -> new ResourceNotFoundException("找不到該講師"));
			existing.setTeacher(teacher);
		}
		return courseRepository.save(existing);
	}

	// 刪除課程（先清除中間表關聯，避免外鍵約束錯誤）
	@Transactional
	public void deleteCourse(Long id) {
		Course course = courseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("找不到該課程"));
		new ArrayList<>(course.getEnrolledUsers()).forEach(user -> user.getCourses().remove(course));
		courseRepository.delete(course);
	}
}
