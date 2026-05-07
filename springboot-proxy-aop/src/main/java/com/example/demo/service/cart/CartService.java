package com.example.demo.service.cart;


/*
 * 購物車
 * 目的:將商品放到購物車
 * 
 * 檢查(利用 AOP):
 * 1.驗證商品 ID 不可以是null
 * 2.商品數量介於 1~100 之間
 * 3.商品價格 > 0
 * */

public interface CartService {
	// 將商品放到購物車
	public void addToCart(String productId , Integer quantity , Double price);
}
