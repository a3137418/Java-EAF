package com.example.demo.service.floor;
/*
 * 門禁管制
 * 1.每一個用戶只能到有授權的樓層
 * 2.當用戶進入樓層時 AOP 會檢查授權
 * 3.未授權進入的用戶，AOP 會 攔截 與 拒絕
 * */

public interface FloorService {
	String enterFloor(String username , int floor);
}
