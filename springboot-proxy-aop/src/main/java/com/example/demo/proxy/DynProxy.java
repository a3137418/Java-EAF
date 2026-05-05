package com.example.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

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
		ClassLoader loader = object.getClass().getClassLoader();
		
		
		// 2.Class<?>[] interfaces => 被代理對象所實作的介面
		Class<?>[] interfaces = object.getClass().getInterfaces();
		
		// 3.InvocationHandler handler => 處理代理的實現
		InvocationHandler handler = (Object proxy, Method method, Object[] args) ->{
			Object result = null;
			
			// 前置通知
			ProxyAspect.before(method, args);
			
			try {
				// 執行業務方法
				result = method.invoke(object, args);
			} catch (Exception e) {
				// 例外通知
				ProxyAspect.throwing(e);
			}finally {
				// 後置通知
				ProxyAspect.end();
			}
			
			return result;
		};
		
		// 4.建立代理實體
		proxyObj = Proxy.newProxyInstance(loader, interfaces, handler);
		return proxyObj;
	}
}
