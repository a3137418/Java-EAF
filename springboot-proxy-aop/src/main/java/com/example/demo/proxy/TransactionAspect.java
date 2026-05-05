package com.example.demo.proxy;

public class TransactionAspect {

	public static void begin() {
		System.out.println("開啟交易");
	}
	
	public static void commit() {
		System.out.println("提交交易");
	}
	
	public static void rollback() {
		System.out.println("交易回滾(交易失敗)");
	}
	
	public static void close() {
		System.out.println("關閉連線");
	}
}
