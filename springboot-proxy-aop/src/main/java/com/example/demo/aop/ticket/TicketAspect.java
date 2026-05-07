package com.example.demo.aop.ticket;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/*
 * 請使用Spring AOP 攔截火車訂票方法
 * 
 * 當使用者訂票時 AOP 要檢查
 * 1.出發站不可空白
 * 2.到達站不可空白
 * 3.出發站與到達站不可相同
 * 4.張數必須介於 1 ~ 6張
 * 5.上述都檢查通過後，才可訂票
 * 
 * 例外拋出可以使用 IllegalArgumentException
 * */


@Component
@Aspect
public class TicketAspect {

	@Around(value = "execution(* com.example.demo.service.ticket.TicketService.bookTicket(..))")
	public Object checkTicket(ProceedingJoinPoint joinPoint ) throws Throwable {
		Object result = null;
		
		Object[] args = joinPoint.getArgs();
		String username = (String) args[0];
		String fromStation = (String)args[1];
		String toStation = (String)args[2];
		int quantity = (int)args[3];
		
		
		//老師
//		 * 1.出發站不可空白
		if(fromStation == null || fromStation.isBlank()) {
			throw new IllegalArgumentException("出發站不可空白");
		}
		
		
//		 * 2.到達站不可空白
		if(toStation == null || toStation.isBlank()) {
			throw new IllegalArgumentException("到達站不可空白");
		}
		
//		 * 3.出發站與到達站不可相同
		if(fromStation.equals(toStation)) {
			throw new IllegalArgumentException("出發站與到達站不可相同");
		}
		
//		 * 4.張數必須介於 1 ~ 6張
		if (quantity<0 || quantity >6) {
			throw new IllegalArgumentException("張數必須介於 1 ~ 6張");
		}
		
//		 * 5.上述都檢查通過後，才可訂票
		result = joinPoint.proceed();
			
		
		
		

//		//我的
//		try {
//			if(fromStation.isBlank() || toStation.isBlank()) {
//				System.out.println("出發站 或 到達站不可空白");
//			}else {
//				if (fromStation == toStation) {
//					System.out.println("出發站 與 到達站不可相同");
//				}else {
//					if (quantity < 0 || quantity >6) {
//						System.out.println("張數必須介於 1 ~ 6張");
//					}else {
//						result = joinPoint.proceed();
//					}
//				}
//			}
//		} catch (IllegalArgumentException e) {
//			System.out.println("錯誤: " + e.getMessage());
//		}
//		
		return result;
		
	}
}
