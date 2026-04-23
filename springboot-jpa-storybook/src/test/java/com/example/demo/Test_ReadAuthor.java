package com.example.demo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;

@SpringBootTest
public class Test_ReadAuthor {

	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Test
	public void read() {
		List<Author> authors = authorRepository.findAll();
		
		authors.forEach(author ->{
			//只能查作者自己的資料，無法查該作者的傳記
			System.out.printf("id: %d%nname: %s%n%n",
					author.getId(), author.getName());
		});
	}
}
