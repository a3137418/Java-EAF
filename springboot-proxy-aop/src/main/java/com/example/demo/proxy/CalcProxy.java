package com.example.demo.proxy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

//代理物件 也要交給 Spring 來管理
@Component
@Primary // 如果有人要注入 Calc，優先注入CalcProxy
public class CalcProxy implements Calc{

	private Calc calc;
	
	//我要注入的是calcImpl 而不是 CalcProxy 自己
	public CalcProxy(@Qualifier("calcImpl") Calc calc) {
		this.calc = calc;
	}
	
	
	
	@Override
	public Integer add(Integer x, Integer y) {
		// 前置通知 : 驗證x,y不可以是null
		if(x == null || y == null) {
			System.out.println("x,y 參數不正確");
			return null;
		}
		//調用業務邏輯
		Integer result = calc.add(x, y);
		return result;
	}

	@Override
	public Integer div(Integer x, Integer y) {
		// 前置通知 : 驗證x,y不可以是null
		if(x == null || y == null) {
			System.out.println("x,y 參數不正確");
			return null;
		}
		try {
			//調用業務邏輯
			Integer result = calc.div(x, y);
			return result;
		} catch (Exception e) {
			//例外通知
			System.out.println("執行時期發生錯誤，原因: " + e.getMessage());
		}
		return null;
	}

}
