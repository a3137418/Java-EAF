package com.example.demo.proxy;

public class Woman implements Person{
	
	@Override
	public void work() {
		//公用邏輯
//		System.out.println("出門");
//		System.out.println("戴口罩");
		//業務邏輯
		System.out.println("Woman 出門買菜");
		
		// 公用邏輯
//		System.out.println("回家脫口罩");
	}
}
