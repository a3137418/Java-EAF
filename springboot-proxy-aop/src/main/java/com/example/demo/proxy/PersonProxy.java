package com.example.demo.proxy;

// 靜態代理
// 用來執行 OR 調用 被代理者的業務邏輯與實現公用邏輯
public class PersonProxy implements Person{
	//代理對象(被代理者:Man or Woman 的實體)
	private Person person;
	
	public PersonProxy(Person person) {
		this.person = person;
	}
	@Override
	public void work() {
		//公用邏輯-前置通知
		System.out.println("出門");
		System.out.println("戴口罩");
		System.out.println("量體溫");
		try {
			//調用業務邏輯
			person.work();
		}catch (Exception e) {
			// 公用邏輯-例外通知
			System.out.println("將例外紀錄下來" + e);
		} finally {
			// 公用邏輯-後置通知
			System.out.println("回家脫口罩");
		}
	}
}
