package com.example.demo.proxy;

public class AccountServiceImpl implements AccountService{
	
	@Override
	public void transfer(String from , String to , int money) {
		System.out.printf("%s 轉帳 %d 元給 %s%n", from,money,to);
		
		if(money > 10000) {
			throw new RuntimeException("轉帳金額過大");
		}
		
		System.out.println("轉帳成功");
		
	}
}
