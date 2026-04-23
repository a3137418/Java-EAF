package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.StoryBook;
import com.example.demo.repository.StoryBookRepository;

@SpringBootTest
public class Test_ReadStockBook {
	
	@Autowired
	private StoryBookRepository storyBookRepository;
	
	@Test
	public void read() {
		//查詢全部
		List<StoryBook> storyBooks = storyBookRepository.findAllByOrderByIdAsc();
		storyBooks.forEach(sb -> {
			System.out.println(sb.getId() + "->" + sb.getName());
		});
		
		//單筆查詢
		Optional<StoryBook> optStoryBook = storyBookRepository.findById(1);
		if(optStoryBook.isPresent()) {
			StoryBook sb = optStoryBook.get();
			System.out.println(sb.getId() + "->" + sb.getName());
		}else {
			System.out.println("查無資料");
		}
	}
	
}
