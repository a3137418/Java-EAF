package com.example.demo.book;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.book.Biography;
import com.example.demo.repository.book.BiographyRepository;

@SpringBootTest
public class Test_ReadBiography {

	
	@Autowired
	private BiographyRepository biographyRepository;
	
	@Test
	public void read() {
		List<Biography> biographies = biographyRepository.findAll();
		
		biographies.forEach(bio ->{
			//查詢傳記時也可以查到作者資料
			System.out.printf("id: %d%ntext: %s%nauthor id: %d%nauthor name: %s%n%n",
					bio.getId(), bio.getText(), bio.getAuthor().getId(), bio.getAuthor().getName());
		});
	}
}
