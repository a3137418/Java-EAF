package com.example.demo.service.floor;

import org.springframework.stereotype.Service;

@Service
public class FloorServiceImpl implements FloorService{

	@Override
	public String enterFloor(String username, int floor) {
		// 商業邏輯
		return username + " 授權進入" + floor + " 樓";
	}

}
