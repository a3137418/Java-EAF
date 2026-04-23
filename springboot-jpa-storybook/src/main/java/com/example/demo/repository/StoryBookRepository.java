package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.StoryBook;

@Repository
public interface StoryBookRepository extends JpaRepository<StoryBook , Integer>{
	//依 id 由小到大排序
	List<StoryBook> findAllByOrderByIdAsc();
	
	//由大到小
	List<StoryBook> findAllByOrderByIdDesc();
}
