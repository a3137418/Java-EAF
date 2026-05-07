package com.example.demo.aop.cart;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CartValidationAspect {
	@Before(value = "execution(* com.example.demo.service.cart.CartService.addToCart(..))")
	public void validateCartParam(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		String productId = (String) args[0];
		Integer quantity = (int)args[1];
		Double price = (double) args[2];
		
		//商品ID 不可以是null
		if(productId == null || productId.isBlank()) {
			throw new IllegalArgumentException("商品ID不可為空");
		}
		
		// 商品數量介於 1~100 之間
		if(quantity < 1 || quantity > 100) {
			throw new IllegalArgumentException("數量必須介於 1~100 之間");
		}
		
		// 商品價格 > 0
		if(price <= 0) {
			throw new IllegalArgumentException("商品價格必須 > 0");
		}
	}
}
