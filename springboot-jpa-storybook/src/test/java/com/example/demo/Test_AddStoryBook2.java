package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Author;
import com.example.demo.model.StoryBook;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.StoryBookRepository;

@SpringBootTest
public class Test_AddStoryBook2 {
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private StoryBookRepository storyBookRepository;
	
	@Test
	public void add() {
		Author au2 = authorRepository.findById(2).get();
		Author au3 = authorRepository.findById(3).get();
		
		StoryBook sb1 = new StoryBook();
		sb1.setName("白雪公主");
		sb1.setAuthor(au2);
		
		StoryBook sb2 = new StoryBook();
		sb2.setName("灰姑娘");
		sb2.setAuthor(au2);
		
		StoryBook sb3 = new StoryBook();
		sb3.setName("老夫子");
		sb3.setAuthor(au3);
		
		// 儲存
		storyBookRepository.save(sb1);
		storyBookRepository.save(sb2);
		storyBookRepository.save(sb3);
		
		System.out.println("add ok");
	}
	
}