package com.example.demo.proxy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Test_Person {
	@Test
	public void test() {
		//一般用法
//		Person man = new Man();
//		Person woman = new Woman();
//		man.work();
//		woman.work();
		
		
		//使用代理模式
		Person man = new PersonProxy(new Man());
		Person woman = new PersonProxy(new Woman());
		Person boy = new PersonProxy(new Boy());
		man.work();
		woman.work();
		boy.work();
	}
}
