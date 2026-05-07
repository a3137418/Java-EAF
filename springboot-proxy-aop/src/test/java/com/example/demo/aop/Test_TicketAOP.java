package com.example.demo.aop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.service.ticket.TicketService;

@SpringBootTest
public class Test_TicketAOP {
	
	@Autowired
	private TicketService ticketService;
	
	@Test
	public void test() {
		try {
			String result1 = ticketService.bookTicket("john", "台北", "台中", 2);
			System.out.println(result1);
		} catch (Throwable e) {
			System.err.println(e.getMessage());
		}
		
		try {
			String result2 = ticketService.bookTicket("john", "", "台中", 2);
			System.out.println(result2);
		} catch (Throwable e) {
			System.err.println(e.getMessage());
		}
		
		try {
			String result3 = ticketService.bookTicket("john", "台北", "", 2);
			System.out.println(result3);
		} catch (Throwable e) {
			System.err.println(e.getMessage());
		}
		
		try {
			String result4 = ticketService.bookTicket("john", "台北", "台北", 2);
			System.out.println(result4);
		} catch (Throwable e) {
			System.err.println(e.getMessage());
		}
		
		try {
			String result5 = ticketService.bookTicket("john", "台北", "台中", 10);
			System.out.println(result5);
		} catch (Throwable e) {
			System.err.println(e.getMessage());
		}
	}
	
}
