package com.example.demo.repository.book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.book.StoryBook;

@Repository
public interface StoryBookRepository extends JpaRepository<StoryBook , Integer>{
	//依 id 由小到大排序
	List<StoryBook> findAllByOrderByIdAsc();
	
	//由大到小
	List<StoryBook> findAllByOrderByIdDesc();
	
	// 依 id 由小到大排序:SQL版
	@Query(value = "select * from story_book order by id asc" , nativeQuery = true)
	List<StoryBook> findAllAsc();
	
	//由大到小:JPQL
	@Query("select s from StoryBook s order by s.id desc")
	List<StoryBook> findAllDesc();
}
