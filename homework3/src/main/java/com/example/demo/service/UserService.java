package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Course;
import com.example.demo.model.User;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CourseRepository courseRepository;

	// 新增使用者（User 即學生，可直接用於選課）
	public User saveUser(User user) {
		if (userRepository.existsByUsername(user.getUsername())) {
			throw new IllegalArgumentException("帳號「" + user.getUsername() + "」已存在，不可重複新增");
		}
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new IllegalArgumentException("Email「" + user.getEmail() + "」已被使用");
		}
		// 設定雙向關聯的反向引用，確保 user_profiles.user_id 正確寫入
		if (user.getUserProfile() != null) {
			user.getUserProfile().setUser(user);
		}
		return userRepository.save(user);
	}

	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	// 使用者（學生）選課
	@Transactional
	public String enrollCourse(Long userId, Long courseId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("找不到該使用者"));
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new ResourceNotFoundException("找不到該課程"));
		boolean alreadyEnrolled = user.getCourses().stream()
				.anyMatch(c -> c.getId().equals(courseId));
		if (alreadyEnrolled) {
			throw new IllegalArgumentException("該使用者已選修此課程，不可重複選課");
		}
		user.getCourses().add(course);
		userRepository.save(user);
		return "選課成功";
	}

	public void deleteUser(Long id) {
		if (!userRepository.existsById(id)) {
			throw new ResourceNotFoundException("找不到該使用者");
		}
		userRepository.deleteById(id);
	}

}
