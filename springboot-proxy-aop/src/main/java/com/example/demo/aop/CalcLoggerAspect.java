package com.example.demo.aop;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component // 被Spring 來管理
@Aspect // 切面程式
@Order(1) // 調用順序
public class CalcLoggerAspect {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	// 建立一個切入點邏輯方法
	@Pointcut(value = "execution(public Integer com.example.demo.proxy.CalcImpl.add(Integer , Integer))")
	public void ptAdd() {}
	@Pointcut(value = "execution(public Integer com.example.demo.proxy.CalcImpl.div(Integer , Integer))")
	public void ptDiv() {}
	
	
	//環繞通知
	@Around("ptDiv()")
	public Object aroundAdvice(ProceedingJoinPoint joinPoint) {
		Object result = null;
		String methodName = joinPoint.getSignature().getName(); // 方法名稱
		Object[] args = joinPoint.getArgs(); // 方法參數
		String dateTime = sdf.format(new Date());
		
		//前置通知
		System.out.printf("Log 環繞通知[%s]: %s %s %n", dateTime , methodName, Arrays.toString(args));
		
		
		try {
			// 執行目標方法
			result = joinPoint.proceed();
			//正常返回通知
			System.out.printf("Log 環繞通知-正常返回通知[%s]: %s %s 正常返回結果:%s %n", dateTime , methodName, Arrays.toString(args) , result);
			
		} catch (Throwable ex) {
			// 異常返回通知
			System.out.printf("Log 環繞通知-異常返回通知[%s]: %s %s 異常返回結果:%s %n", dateTime , methodName, Arrays.toString(args) , ex);
		}finally {
			// 後置通知
			System.out.printf("Log 環繞通知-後置通知[%s]: %s %s %n", dateTime , methodName, Arrays.toString(args));
		}
		return result;
	}
	
	
	
	
	
	
	// 前置通知(Advice)
//	@Before(value = "execution(public Integer com.example.demo.proxy.CalcImpl.add(Integer , Integer))") // 僅針對加法做前置通知
//	@Before(value = "execution(public Integer com.example.demo.proxy.CalcImpl.div(Integer , Integer))") 
//	@Before(value = "execution(public Integer com.example.demo.proxy.CalcImpl.*(Integer , Integer))") // *代表所有切面程式
//	@Before(value = "execution(public Integer com.example.demo.proxy.CalcImpl.*(..))") // ..表示任意參數的組合
//	@Before(value = "execution(public Integer com.example.demo.proxy.*.*(..))") //代表：任何回傳型別、demo 套件下所有子套件、所有類別、所有方法、所有參數都攔截。
//	@Before(value = "execution(public * *(..))") //全域攔截
//	@Before(value = "ptAdd()")
	@Before(value = "ptAdd() || ptDiv()" ) // 切入點表達式支援邏輯運算子: &&，||，!
	public void before(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName(); // 取得方法名稱
		Object[] args = joinPoint.getArgs();
		String dateTime = sdf.format(new Date());
		//Log紀錄
		System.out.printf("Log 前置通知[%s]: %s %s %n", dateTime , methodName, Arrays.toString(args));
	}
	
	
	
	// 後置通知: 不論是否會發生例外都會執行
	@After(value = "ptDiv()")
	public void end(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName(); // 取得方法名稱
		Object[] args = joinPoint.getArgs();
		String dateTime = sdf.format(new Date());
		//Log紀錄
		System.out.printf("Log 後置通知[%s]: %s %s %n", dateTime , methodName, Arrays.toString(args));
	}
	
	// 正常返回通知
	@AfterReturning(value = "ptDiv()" , returning = "result")
	public void afterReturning(JoinPoint joinPoint , Object result) {
		String methodName = joinPoint.getSignature().getName(); // 取得方法名稱
		Object[] args = joinPoint.getArgs();
		String dateTime = sdf.format(new Date());
		//Log紀錄
		System.out.printf("Log 正常返回通知[%s]: %s %s 正常返回結果:%s %n", dateTime , methodName, Arrays.toString(args) , result);
	}
	
	
	// 異常返回通知
	@AfterThrowing(value = "ptDiv()" , throwing = "ex")
	public void afterThrowing(JoinPoint joinPoint , Throwable ex) {
		String methodName = joinPoint.getSignature().getName(); // 取得方法名稱
		Object[] args = joinPoint.getArgs();
		String dateTime = sdf.format(new Date());
		//Log紀錄
		System.out.printf("Log 異常返回通知[%s]: %s %s 異常返回結果:%s %n", dateTime , methodName, Arrays.toString(args) , ex);
	}
	
}
