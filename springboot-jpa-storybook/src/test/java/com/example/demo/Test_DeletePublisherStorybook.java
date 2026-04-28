package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.repository.PublisherRepository;

@SpringBootTest
public class Test_DeletePublisherStorybook {
	@Autowired
	private PublisherRepository publisherRepository;
	
	@Test
	@Transactional
	@Commit // 測試環境中刪除時，要加上@Transactional + @Commit
	public void delete() {
		publisherRepository.deleteStoryBookFromPublisher(1, 1);
		System.out.println("Delete OK");
	}
}
