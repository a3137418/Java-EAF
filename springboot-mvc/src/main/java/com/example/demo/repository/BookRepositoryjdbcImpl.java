package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Book;

@Repository
public class BookRepositoryjdbcImpl implements BookRepository{

	@Autowired
	private JdbcTemplate jdbcTemplate; //自動綁定spring 的 JdbcTemplate物件
	
	@Override
	public List<Book> findAllBooks() {
		String sql = "select id, name, price, amount, pub from book";
		//利用 BeanPropertyRowMapper(Book.class) 會自動將資料表中查到的每一筆紀錄 注入到 Book 物件中
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Book.class));
	}

	@Override
	public Optional<Book> getBookById(Integer id) {
		String sql = "select id, name, price, amount, pub from book where id=?";
		// 查單筆, 注意:若沒查到會拋出 EmptyResultDataAccessException
		try {
			Book book = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Book.class), id);
			return Optional.of(book);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public boolean addBook(Book book) {
		String sql = "insert into book(name,price,amount,pub) values(?,?,?,?)";
		try {
			int rows = jdbcTemplate.update(sql,book.getName(),book.getPrice(),book.getAmount(),book.getPub());
			return rows > 0;
		} catch (DuplicateKeyException e) {
			return false;
		}
	}

	@Override
	public boolean updateBook(Integer id, Book book) {
		String sql = "update book set name=? , price=? , amount=? , pub=? where id=?";
		try {
			int rows = jdbcTemplate.update(sql,book.getName(),book.getPrice(),book.getAmount(),book.getPub(),id);
			return rows > 0;
		}catch (DuplicateKeyException e) {
			return false;
		}
	}

	@Override
	public boolean deleteBook(Integer id) {
		String sql = "delete from book where id = ?";
		try {
			int rows = jdbcTemplate.update(sql,id);
			return rows > 0;
		} catch (Exception e) {
			System.out.println("刪除失敗" +e.getMessage());
		}
		return false;
	}

}
