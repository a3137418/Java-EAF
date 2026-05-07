package com.example.demo.service.ticket;

import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService{

	@Override
	public String bookTicket(String username, String fromStation, String toStation, int quantity) {
		return username + "成功訂購火車票: " + fromStation + "到" + toStation + "，張數: " + quantity;
	}

}
