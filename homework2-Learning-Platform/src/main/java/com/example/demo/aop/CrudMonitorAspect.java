package com.example.demo.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CrudMonitorAspect {

	@Around("execution(* com.example.demo.service.*.*(..))")
	public Object monitorCrud(ProceedingJoinPoint joinPoint) throws Throwable{
		
		// 取得方法名稱
		String methodName = joinPoint.getSignature().getName();
		
		// 取得參數內容
		String args = Arrays.toString(joinPoint.getArgs());
		
		// 方法執行前印出
		System.out.println("[AOP CRUD 監控] 準備執行資料庫操作方法: "
				+ methodName + " | 接收參數: " + args);
		
		// 紀錄開始時間
		long start = System.currentTimeMillis();
		
		// 執行原本的方法
		Object result = joinPoint.proceed();
		
		// 計算耗時
		long elapsed = System.currentTimeMillis() - start;
		
		// 方法執行後印出
		System.out.println("[AOP CRUD 監控] 資料庫操作結束: "
				+ methodName + " | 操作耗時: " + elapsed + " 毫秒");
		
		
		
		return result;
		
	}
}
