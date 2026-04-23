package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;

@SpringBootTest
public class Test_AddAuthor {
	
	@Autowired
	private AuthorRepository authorRepository;
	
	
	@Test
	public void add() {
		Author a1 = new Author();
		a1.setName("John");
		
		Author a2 = new Author();
		a2.setName("Mary");
		
		Author a3 = new Author();
		a3.setName("Bob");
		
		authorRepository.save(a1);
		authorRepository.save(a2);
		authorRepository.save(a3);
		
		System.out.println("Add OK");
	}
	
}