package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
	private String message;
	T data;
	
	//成功回應
	public static <T> ApiResponse<T> success(String message, T data){
		return new ApiResponse<T>(message,data);
	}
	
	//失敗回應
	public static <T> ApiResponse<T> error(String message){
		return new ApiResponse<T>(message,null);
	}
}
