package com.example.demo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;



@SpringBootTest
public class Test_ReadAuthor2 {

	@Autowired
	private AuthorRepository authorRepository;
	
	@Test
	@Transactional // 因為 storyBooks 是 LAZY 延遲載入，所以要加入
				   // 若不想加入 @Transactional 則必須 storyBooks 改 fetch = FetchType.EAGER
				   // 請參考 Author.java 的 @OneToMany 的設定
	public void read() {
		//顯示作者有出版幾本書 ?
		List<Author> authors = authorRepository.findAll();
		
		
		authors.forEach(au ->{
			System.out.printf("作者: %s 書籍數量: %d%n" , au.getName() , au.getStoryBooks().size());
		});
	}
	
	@Test
	public void read2() {
		//顯示作者名稱
		List<Author> authors = authorRepository.findAll();
		
		
		authors.forEach(au ->{
			System.out.printf("作者: %s" , au.getName());
		});
	}
}
