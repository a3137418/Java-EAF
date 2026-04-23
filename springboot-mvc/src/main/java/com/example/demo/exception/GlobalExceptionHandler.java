package com.example.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
//統一處理所有 controller 所發生的例外

import com.example.demo.model.Book;
import com.example.demo.response.ApiResponse;
@RestControllerAdvice
public class GlobalExceptionHandler {
	//統一處理 BookException
	@ExceptionHandler(BookException.class)
	public ResponseEntity<ApiResponse<Book>> handleBookException(BookException e){
		return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
	}
	
}
