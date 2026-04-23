package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Author;
import com.example.demo.model.Biography;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BiographyRepository;

@SpringBootTest
public class Test_AddBiography {

	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private BiographyRepository biographyRepository;
	
	@Test
	public void add() {
		//取得作者資料
		Author a1 = authorRepository.findById(1).get();
		Author a2 = authorRepository.findById(2).get();
		Author a3 = authorRepository.findById(3).get();
		
		//建立傳記
		Biography bio1 =new Biography();
		bio1.setText("這是我的資料 AAA");
		bio1.setAuthor(a1); // 建立關聯關係
		
		Biography bio2 =new Biography();
		bio2.setText("這是我的資料 BBB");
		bio2.setAuthor(a2); // 建立關聯關係
		
		Biography bio3 =new Biography();
		bio3.setText("這是我的資料 CCC");
		bio3.setAuthor(a3); // 建立關聯關係
		
		//儲存
		biographyRepository.save(bio1);
		biographyRepository.save(bio2);
		biographyRepository.save(bio3);
		
		System.out.println("add ok!");
		
		
		
	}
}
