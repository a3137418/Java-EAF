package com.example.demo;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.StoryBook;
import com.example.demo.repository.StoryBookRepository;

@SpringBootTest
public class Test_UpdateStoryBook {
	
	@Autowired
	private StoryBookRepository storyBookRepository;
	
	@Test
	public void update() {
		// 1.找到要修改的資料
		Optional<StoryBook> optStoryBook = storyBookRepository.findById(1);
		if(optStoryBook.isPresent()) {
			StoryBook sb = optStoryBook.get();
			sb.setName("小叮噹");
			storyBookRepository.save(sb);
			System.out.println("修改完畢");
		}else {
			System.out.println("查無資料");
		}
	}
}
