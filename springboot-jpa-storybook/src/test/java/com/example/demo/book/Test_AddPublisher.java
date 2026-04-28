package com.example.demo.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.book.Publisher;
import com.example.demo.model.book.StoryBook;
import com.example.demo.repository.book.PublisherRepository;
import com.example.demo.repository.book.StoryBookRepository;

@SpringBootTest
public class Test_AddPublisher {
	
	@Autowired
	private StoryBookRepository storyBookRepository;
	
	@Autowired
	private PublisherRepository publisherRepository;
	
	@Test
	public void add() {
		StoryBook sb1 = storyBookRepository.findById(1).get();
		StoryBook sb2 = storyBookRepository.findById(2).get();
		StoryBook sb3 = storyBookRepository.findById(3).get();
		StoryBook sb4 = storyBookRepository.findById(4).get();
		StoryBook sb5 = storyBookRepository.findById(5).get();
		
		Publisher pub1 = new Publisher();
		pub1.setName("巨匠");
		pub1.addStoryBook(sb1);
		pub1.addStoryBook(sb2);
		
		Publisher pub2 = new Publisher();
		pub2.setName("華納");
		pub2.addStoryBook(sb1);
		pub2.addStoryBook(sb2);
		pub2.addStoryBook(sb4);
		
		Publisher pub3 = new Publisher();
		pub3.setName("天下");
		pub3.addStoryBook(sb1);
		pub3.addStoryBook(sb3);
		pub3.addStoryBook(sb5);
		
		publisherRepository.save(pub1);
		publisherRepository.save(pub2);
		publisherRepository.save(pub3);
		
		System.out.println("Add OK");
		
		
	}
	
}