package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{

	boolean existsByTitle(String title);

	boolean existsByTitleAndIdNot(String title, Long id);
	
	List<Course> findByTeacherId(Long teacherId);
}
