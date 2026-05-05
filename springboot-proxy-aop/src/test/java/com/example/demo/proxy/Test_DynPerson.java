package com.example.demo.proxy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Test_DynPerson {
	
	@Test
	public void test() {
		// 動態代理測試
		DynProxy dynProxy = new DynProxy(new Man());
		Person man = (Person) dynProxy.getProxy(); // 取得代理物件
		man.work();
		
		DynProxy dynProxy2 = new DynProxy(new Woman());
		Person woman = (Person) dynProxy2.getProxy(); // 取得代理物件
		woman.work();
		
		DynProxy dynProxy3 = new DynProxy(new Boy());
		Person boy = (Person) dynProxy3.getProxy(); // 取得代理物件
		boy.work();
	}
}
