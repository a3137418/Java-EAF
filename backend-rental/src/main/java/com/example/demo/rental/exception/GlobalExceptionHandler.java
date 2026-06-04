package com.example.demo.rental.exception;

import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.rental.model.dto.ApiResponse;

// 全域例外處理器
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	// 處理 DTO 驗證失敗
	// 進行資料驗證,前端傳入的參數不符合規則時
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {
		// 將所有欄位錯誤訊息組合成一段文字, 方便前端顯示
		String message = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.collect(Collectors.joining("; "));
		
		return ResponseEntity
				.badRequest()
				.body(ApiResponse.error(400, message));
	}
	
	// 處理商業邏輯例外
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiResponse<Object>> handleBusiness(BusinessException ex) {
		return ResponseEntity
				.badRequest()
				.body(ApiResponse.error(400, ex.getMessage()));
	}
	
	// 處理資源不存在的例外
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse<Object>> handleNotFound(ResourceNotFoundException ex) {
		return ResponseEntity
				.badRequest()
				.body(ApiResponse.error(404, ex.getMessage()));
		//return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(404, ex.getMessage()));
	}
	
	// 處理權限不足的例外
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiResponse<Object>> handleAccessDenied(AccessDeniedException ex) {
		return ResponseEntity
				.status(HttpStatus.FORBIDDEN)
				.body(ApiResponse.error(403, "權限不足"));
	}
	
	// 處理未預期的例外
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Object>> handleException(Exception ex) {
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ApiResponse.error(500, "系統發生未預期的錯誤 !"));
	}
	
	
}