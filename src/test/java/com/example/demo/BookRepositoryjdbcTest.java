package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;

@SpringBootTest
public class BookRepositoryjdbcTest {
	
	@Autowired
	@Qualifier("bookRepositoryjdbcImpl") //指定實現類
	private BookRepository bookRepository;
	
	@Test
	void queryTest() {
		List<Book> books = bookRepository.findAllBooks();
		books.forEach(System.out::println);
	}
	@Test
	void getTest() {
		Optional<Book> optBook = bookRepository.getBookById(1);
		if(optBook.isPresent()) {
			Book book = optBook.get();
			System.out.println(book);
		} else {
			System.out.println("查無此書");
		}
	}
	
	@Test
	void addTest() {
		Book book = new Book(0,"Java",50.5,120,true);
		boolean check = bookRepository.addBook(book);
		System.out.println(check ? "新增成功" : "新增失敗");
	}
	
	@Test
	void updateTest() {
		//先取得書籍
		Optional<Book> optBook = bookRepository.getBookById(1);
		if(optBook.isPresent()) {
			Book book = optBook.get();
			//修改必要資料
			book.setName("中華一番");
			book.setAmount(33);
			book.setPrice(22.2);
			book.setPub(true);
			//進行修改
			boolean check = bookRepository.updateBook(book.getId(), book);
			System.out.println(check ? "修改成功" : "修改失敗");
		} else {
			System.out.println("查無此書");
		}
	}
	
	//@Test
	void deleteTest() {
		Integer id = 9;
		boolean check = bookRepository.deleteBook(id);
		System.out.println(check ? "刪除成功" : "刪除失敗");
	}
}
