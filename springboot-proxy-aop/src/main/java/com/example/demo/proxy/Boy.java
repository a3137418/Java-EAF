package com.example.demo.proxy;

public class Boy implements Person{

	@Override
	public void work() {
		// 業務邏輯
		System.out.println("Boy 到學校上課");
		
		throw new RuntimeException("功課忘了帶...");
	}
	
}
