package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Author;
import com.example.demo.model.StoryBook;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.StoryBookRepository;

@SpringBootTest
public class Test_UpateStoryBookAuthor {
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private StoryBookRepository storyBookRepository;
	
	@Test
	public void updateAuthor() {
		//找到作者物件
		Author author = authorRepository.findById(1).get();
		
		//將指定書籍找到並設定作者
		StoryBook sb1 = storyBookRepository.findById(1).get();
		StoryBook sb2 = storyBookRepository.findById(2).get();
		
		sb1.setAuthor(author);
		sb2.setAuthor(author);
		
		storyBookRepository.save(sb1);
		storyBookRepository.save(sb2);
		
		System.out.println("書籍設定作者完成");
	}
	
	
}
