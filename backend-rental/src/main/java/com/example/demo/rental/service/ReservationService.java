package com.example.demo.rental.service;

import org.springframework.stereotype.Service;


/*
 * 預約服務層
 * 
 * 負責處理租用系統中的預約核心商業邏輯
 * 1.建立預約
 * 2.查詢個人預約
 * 3.查詢全部預約
 * 4.取消預約
 * 5.核准預約
 * 6.退回預約
 * 
 * 整合使用者資料，租用項目資料，預約狀態判斷，時間衝突檢查，金額計算，交易控制
 * 
 * 重點商業規則
 * 1.結束時間 > 開始時間
 * 2.租用項目必須為 AVAILABLE 才可以預約
 * 3.PENDING 與 APPROVED 狀態的預約不可以與新預約時段重疊
 * 4.會員只能取消自己的預約
 * 5.只有 PENDING 狀態可以被核准或退回
 * */
@Service
public class ReservationService {

}
