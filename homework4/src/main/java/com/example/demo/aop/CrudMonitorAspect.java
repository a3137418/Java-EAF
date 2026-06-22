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
	public Object monitorCrud(ProceedingJoinPoint joinPoint) throws Throwable {

		String methodName = joinPoint.getSignature().getName();
		String args = Arrays.toString(joinPoint.getArgs());

		System.out.println("[AOP CRUD 監控] 準備執行資料庫操作方法: "
				+ methodName + " | 接收參數: " + args);

		long start = System.currentTimeMillis();

		try {
			Object result = joinPoint.proceed();
			long elapsed = System.currentTimeMillis() - start;
			System.out.println("[AOP CRUD 監控] 資料庫操作結束: "
					+ methodName + " | 操作耗時: " + elapsed + " 毫秒");
			return result;
		} catch (Throwable ex) {
			long elapsed = System.currentTimeMillis() - start;
			System.out.println("[AOP CRUD 監控] 資料庫操作異常結束: "
					+ methodName + " | 操作耗時: " + elapsed + " 毫秒 | 錯誤: " + ex.getMessage());
			throw ex;
		}
	}
}
