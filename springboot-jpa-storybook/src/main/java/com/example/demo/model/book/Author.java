package com.example.demo.model.book;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 50 , nullable = false)
	private String name;
	
	
	// mappedBy設定關聯被動式，如此查作者也可以查到傳記
	@OneToOne(mappedBy = "author")
	public Biography biography;
	
//	@OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
	@OneToMany(mappedBy = "author")
	private List<StoryBook> storyBooks;
}
