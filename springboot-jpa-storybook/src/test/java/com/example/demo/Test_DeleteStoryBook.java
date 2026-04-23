package com.example.demo;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.StoryBook;
import com.example.demo.repository.StoryBookRepository;

@SpringBootTest
public class Test_DeleteStoryBook {

	@Autowired
	private StoryBookRepository storyBookRepository;
	
	@Test
	public void delete() {
		Optional<StoryBook> optStoryBook = storyBookRepository.findById(2);
		
		if(optStoryBook.isPresent()) {
//			storyBookRepository.delete(optStoryBook.get());
			storyBookRepository.deleteById(2);
			System.out.println("刪除成功");
		}else {
			System.out.println("查無資料!");
		}
	}
}
