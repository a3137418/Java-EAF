package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@ManyToMany
	@JoinTable(
			name = "student_course",// 中間表的名稱
			joinColumns = @JoinColumn(name = "student_id"), // 本身(Student)的外鍵
			inverseJoinColumns = @JoinColumn(name = "course_id") // 對方(Course)的外鍵
	)
	private List<Course> courses = new ArrayList<>();
}
