package com.example.demo.service.ticket;

// 火車票訂票
public interface TicketService {
	String bookTicket(String username , String fromStation , String toStation , int quantity);
}
