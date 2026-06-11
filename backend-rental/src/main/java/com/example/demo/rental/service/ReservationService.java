package com.example.demo.rental.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.rental.exception.BusinessException;
import com.example.demo.rental.exception.ResourceNotFoundException;
import com.example.demo.rental.mapper.ReservationMapper;
import com.example.demo.rental.model.dto.reservation.ReservationCreateRequest;
import com.example.demo.rental.model.dto.reservation.ReservationResponse;
import com.example.demo.rental.model.entity.AppUser;
import com.example.demo.rental.model.entity.RentalItem;
import com.example.demo.rental.model.entity.Reservation;
import com.example.demo.rental.model.enums.ReservationStatus;
import com.example.demo.rental.repository.AppUserRepository;
import com.example.demo.rental.repository.ReservationRepository;

/**
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
 * 整合使用者資料, 租用項目資料, 預約狀態判斷, 時間衝突檢查, 金額計算, 交易控制
 * 
 * 重點商業規則
 * 1.結束時間必須晚於開始時間
 * 2.租用項目必須為 AVAILABLE 才可以預約
 * 3.PENDING 與 APPROVED 狀態的預約不可與新預約時段重疊
 * 4.會員只能取消自己的預約
 * 5.只有 PENDING 狀態可以被核准或退回
 * 
 * */
@Service
public class ReservationService {
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private RentalItemService rentalItemService;
	
	/**
	 * 建立新的租用預約
	 * 
	 * 流程:
	 * 1.驗證預約開始時間與結束時間是否合法
	 * 2.依照 username 查詢目前登入的使用者
	 * 3.依照 itemId 查詢租用項目
	 * 4.確認租用項目狀態是否可租用
	 * 5.檢查同一租用項目在指定時段是否已被預約
	 * 6.依照租用時數與每小時價格計算總金額
	 * 7.建立 PENDING 狀態的預約資料
	 * 
	 * */
	
	@Transactional
	public ReservationResponse create(String username, ReservationCreateRequest request) {
		
		if(!request.getStartTime().isBefore(request.getEndTime())) {
			throw new BusinessException("結束時間必須晚於開始時間");
		}
		
		// 租用人
		AppUser user = appUserRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("找不到使用者"));
		
		// 租用項目
		RentalItem item = rentalItemService.getEntity(request.getItemId());
		
		// 時間是否衝突 ?
		boolean conflict = reservationRepository.existsOverlap(
				item.getId(), 
				List.of(ReservationStatus.PENDING, ReservationStatus.APPROVED), 
				request.getEndTime(), 
				request.getStartTime());
		
		if(conflict) {
			throw new BusinessException("此時段已經有人預約, 請更換時間");
		}
		
		// 計算租金
		long hours = Duration.between(request.getStartTime(), request.getEndTime()).toHours();
		BigDecimal totalAmount = item.getPricePerHour().multiply(new BigDecimal(hours));
		
		// 預約
		Reservation reservation = new Reservation();
		reservation.setUser(user);
		reservation.setItem(item);
		reservation.setStartTime(request.getStartTime());
		reservation.setEndTime(request.getEndTime());
		reservation.setTotalAmount(totalAmount);
		reservation.setStatus(ReservationStatus.PENDING);
		reservation.setNote(request.getNote());
		
		reservation = reservationRepository.save(reservation);
		
		return ReservationMapper.toResponse(reservation);
	}
	
	/**
	 * 查詢目前登入會員自己的預約紀錄
	 * 
	 * */
	@Transactional(readOnly = true)
	public List<ReservationResponse> findMine(String username) {
		AppUser user = appUserRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("找不到使用者"));
		
		List<Reservation> list = reservationRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
		
		return list.stream().map(ReservationMapper::toResponse).toList();
	}
	
	
	/**
	 * 查詢所有的預約
	 * 
	 * */
	@Transactional(readOnly = true)
	public List<ReservationResponse> findAll() {
		List<Reservation> list = reservationRepository.findAllByOrderByCreatedAtDesc();
		return list.stream().map(ReservationMapper::toResponse).toList();
	}
	
	/**
	 * 取消會員自己的預約
	 * 
	 * */
	@Transactional
	public ReservationResponse cancelMine(String username, Long id) {
		AppUser user = appUserRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("找不到使用者"));
		
		Reservation reservation = getEntity(id);
		
		if(!reservation.getUser().getId().equals(user.getId())) {
			throw new BusinessException("只能取消自己的預約");
		}
		
		if(reservation.getStatus() != ReservationStatus.PENDING && reservation.getStatus() != ReservationStatus.APPROVED) {
			throw new BusinessException("目前狀態不可取消");
		}
		
		reservation.setStatus(ReservationStatus.CANCELED);
		return ReservationMapper.toResponse(reservation);
		
	}
	
	/**
	 * 核准指定預約-管理者
	 * 
	 * 此方法通常是給管理者使用
	 * 只有 PENDING 狀態的預約才可以核准, 核准後變為 APPROVED
	 * 
	 * */
	@Transactional
	public ReservationResponse approve(Long id) {
		Reservation reservation = getEntity(id);
		if(reservation.getStatus() != ReservationStatus.PENDING) {
			throw new BusinessException("只有 PENDING 狀態才可以核准");
		}
		reservation.setStatus(ReservationStatus.APPROVED);
		return ReservationMapper.toResponse(reservation);
		
	}
	
	/*
	 * 退回指定預約-管理者
	 * 
	 * 此方法通常是給管理者使用
	 * 只有 PENDING 狀態的預約才可以退回, 退回後變為 REJECTED
	 * 
	 * 退回後不會刪除預約資料，而是保留歷史紀錄
	 * 
	 * */
	
	@Transactional
	public ReservationResponse reject(Long id) {
		Reservation reservation = getEntity(id);
		if (reservation.getStatus() != ReservationStatus.PENDING) {
			throw new BusinessException("只有 PEDING 狀態才可以退回");
		}
		reservation.setStatus(ReservationStatus.REJECTED);
		return ReservationMapper.toResponse(reservation);
	}
	
	
	
	private Reservation getEntity(Long id) {
		return reservationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("找不到預約 id=" + id));
	}
	
}