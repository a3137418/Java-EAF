package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Teacher;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.TeacherRepository;

@Service
public class TeacherService {

    private final CourseRepository courseRepository;

	@Autowired
	private TeacherRepository teacherRepository;

    TeacherService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
	
	// 新增講師
	public Teacher saveTeacher(Teacher teacher) {
		return teacherRepository.save(teacher);
	}
	
	// 查詢所有講師
	public List<Teacher> findAllTeachers(){
		return teacherRepository.findAll();
	}
	
	// 修改講師
	public void updateTeacher(Long id , Teacher updated) {
		
	}
	
}
