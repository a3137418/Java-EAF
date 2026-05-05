package com.example.demo.aop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.proxy.Calc;

@SpringBootTest
public class Test_CalcAOP {

	@Autowired
	private Calc calc;
	
	@Test
	public void test() {
		Integer result1 = calc.add(20, 10);
		System.out.println("加法結果: " + result1);
		
		Integer result2 = calc.div(20, 10);
		System.out.println("除法結果: " + result2);
	}
}
