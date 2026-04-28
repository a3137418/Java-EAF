package com.example.demo.book;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.book.StoryBook;
import com.example.demo.repository.book.StoryBookRepository;

@SpringBootTest
public class Test_ReadStoryBook2 {

	
	@Autowired
	private StoryBookRepository storyBookRepository;
	
	@Test
	public void read() {
		List<StoryBook> storyBooks = storyBookRepository.findAll();
		
		storyBooks.forEach(sb ->{
			System.out.printf("書名: %s 作者: %s%n", sb.getName(), sb.getAuthor().getName());
		});
	}
}
