package com.example.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.demo.controller.BikeController;
import com.example.demo.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final BikeController bikeController;

    GlobalExceptionHandler(BikeController bikeController) {
        this.bikeController = bikeController;
    }
	//統一處理 BikeException
	@ExceptionHandler(BikeException.class)
	public ResponseEntity<ApiResponse<String>> hnadleBikeException(BikeException e){
		return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
	}
}
