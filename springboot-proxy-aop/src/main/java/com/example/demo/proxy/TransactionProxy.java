package com.example.demo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TransactionProxy {
	public Object object;
	
	public TransactionProxy(Object object) {
		this.object = object;
	}
	
	public Object getProxy() {
		ClassLoader loader = object.getClass().getClassLoader();
		Class<?>[] interfaces = object.getClass().getInterfaces();
		InvocationHandler handler = (Object proxy, Method method, Object[] args) ->{
			Object result = null;
			try {
				TransactionAspect.begin();
				
				result = method.invoke(object, args);
				
				TransactionAspect.commit();
			} catch (Exception e) {
				TransactionAspect.rollback();
				System.out.println("錯誤原因:" + e.getCause().getMessage());
			}finally {
				TransactionAspect.close();
			}
			
			return result;
		};
		
		return Proxy.newProxyInstance(loader, interfaces, handler);
	}
	
}
