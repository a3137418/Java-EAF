package com.example.demo.proxy;


// 動態代理
// 可以代理任何物件
public class DynProxy {
	//被代理的物件
	public Object object;
	
	public DynProxy(Object object) {
		this.object = object;
	}
	
	// 取得代理物件
	public Object getProxy() {
		Object proxyObj = null;
		
		// 1.ClassLoader loader => 類別載入器
		
		// 2.Class<?>[] interfaces => 被代理對象所實作的介面
		
		// 3.InvocationHandler handler => 處理代理的實現
		
		
		
		return proxyObj;
	}
	
	
	
}
