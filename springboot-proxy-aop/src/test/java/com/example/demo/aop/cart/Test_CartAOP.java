package com.example.demo.aop.cart;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.service.cart.CartService;

@SpringBootTest
public class Test_CartAOP {

	
	@Autowired
	private CartService cartService;
	
	
	@Test
	public void test() {
		String productId = "A01"; // ""
		Integer quantity = 10; // 0
		Double price = 15.5; // 0
		
		try {
			cartService.addToCart(productId, quantity, price);
		} catch (Throwable e) {
			System.out.println(e.getMessage());
		}
		
	}
}
