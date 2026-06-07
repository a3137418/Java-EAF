package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Teacher;
import com.example.demo.repository.TeacherRepository;



@Service
public class TeacherService {

	@Autowired
	private TeacherRepository teacherRepository;
	
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
	
	
}
