package com.example.demo.service.cart;

import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService{
	@Override
	public void addToCart(String productId , Integer quantity , Double price ) {
		// 商業邏輯
		// ...
		System.out.printf("已將商品ID: %s 數量: %d 價格: %.1f 加入購物車" , productId , quantity , price);
	}
}
