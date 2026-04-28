package com.example.demo.model.book;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity
public class Publisher {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 50, nullable = false)
	private String name;
	
	@ManyToMany
	@JoinTable(
			name = "publisher_storybook", // 中間表的名稱
			joinColumns = @JoinColumn(name = "publisher_id"),
			inverseJoinColumns = @JoinColumn(name = "storybook_id")
	)
	private List<StoryBook> storyBooks;
	
	// 自訂一個新增書籍的方法
	public void addStoryBook(StoryBook storyBook) {
		if(storyBooks == null) {
			storyBooks = new CopyOnWriteArrayList<>();
		}
		storyBooks.add(storyBook);
	}
}