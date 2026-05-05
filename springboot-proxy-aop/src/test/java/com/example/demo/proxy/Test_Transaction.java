package com.example.demo.proxy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Test_Transaction {

	@Test
	public void test() {
		AccountService proxy = (AccountService)new TransactionProxy(new AccountServiceImpl()).getProxy();
		
		proxy.transfer("小明", "小華", 5000);
		
		System.out.println("------------------");
		
		proxy.transfer("小明", "小華", 20000);

	}
}
