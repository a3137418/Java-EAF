package com.example.demo.proxy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Test_DynCalc {

	@Test
	public void test() {
		DynProxy dyn = new DynProxy(new CalcImpl());
		Calc calc = (Calc) dyn.getProxy();
		
		System.out.println(calc.add(20, 10));
		System.out.println(calc.div(20, 10));
		System.out.println(calc.add(20, 0));
		System.out.println(calc.div(20, 0));
	}
}
