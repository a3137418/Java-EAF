package com.example.demo.rental.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

	@Around("execution(* com.example.demo.rental.controller..*(..)) || execution(* com.example.demo.rental.service..*(..))")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		String methodName = joinPoint.getSignature().toShortString();
		try {
			Object result = joinPoint.proceed();
			long diff = System.currentTimeMillis() - start;
			log.info("{}執行成功，耗時 {} ms", methodName , diff);
			return result;
		} catch (Throwable ex) {
			long diff = System.currentTimeMillis() - start;
			log.info("{}執行失敗，耗時 {} ms ，原因:{}", methodName , diff , ex.getMessage());
			throw ex;
		}
		
	}
}
