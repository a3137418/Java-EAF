package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.StoryBook;
import com.example.demo.repository.StoryBookRepository;

@SpringBootTest
public class Test_AddStoryBook {
	
	@Autowired
	private StoryBookRepository storyBookRepository;
	
	@Test
	public void add() {
		
		StoryBook sb1 = new StoryBook();
		sb1.setName("多拉A夢");
		
		StoryBook sb2 = new StoryBook();
		sb2.setName("城市獵人");
		
		// 儲存
		storyBookRepository.save(sb1);
		storyBookRepository.save(sb2);
		
		System.out.println("add ok");
	}
	
}