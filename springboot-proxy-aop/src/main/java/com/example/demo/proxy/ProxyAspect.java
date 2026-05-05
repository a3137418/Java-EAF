package com.example.demo.proxy;

import java.lang.reflect.Method;
import java.util.Arrays;

// 用來集中所有的公用邏輯(XX通知) Advice
public class ProxyAspect {
	
	// 前置通知(Advice)
	 public static void before(Method method , Object[] args) {
		 System.out.println("前置通知: 方法:" + method.getName() + " 參數:" + Arrays.toString(args));
	 }
	// 例外通知(Advice)
	 public static void throwing(Exception e) {
		 System.out.println("例外通知:" + e.getCause().getMessage());
	 }
	// 後置通知(Advice)
	 public static void end() {
		 System.out.println("後置通知");
	 }
}
