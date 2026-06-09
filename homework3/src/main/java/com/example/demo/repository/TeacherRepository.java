package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long>{

	boolean existsByName(String name);

	boolean existsByNameAndIdNot(String name, Long id);
}
