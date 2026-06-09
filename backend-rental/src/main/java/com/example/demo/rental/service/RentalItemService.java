package com.example.demo.rental.service;

import java.util.List;
import com.example.demo.rental.repository.ReservationRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.rental.exception.ResourceNotFoundException;
import com.example.demo.rental.mapper.RentalItemMapper;
import com.example.demo.rental.model.dto.item.RentalItemRequest;
import com.example.demo.rental.model.dto.item.RentalItemResponse;
import com.example.demo.rental.model.entity.RentalItem;
import com.example.demo.rental.repository.AppUserRepository;
import com.example.demo.rental.repository.RentalItemRepository;


/*
 * 租用項目服務層
 * 此類別負責處理租用項目的核心業務邏輯
 * 查詢租用項目，新增租用項目，修改租用項目，刪除租用項目
 * 
 * 在系統分層架構中，本類別位於 Controller 與 Respository 之間
 * Controller 負責接收 HTTP 請求
 * Service 負責商業邏輯
 * Respository 負責實際資料庫存取
 * 
 * 本類別不直接回傳 Entity 給前端，而是透過 RentalItemMapper
 * 將 Entity 轉換為 Response DTO
 * 以降低資料庫模型與 API 回應格式的耦合
 * */
@Service
public class RentalItemService {

    private final AppUserRepository appUserRepository;

    private final ReservationRepository reservationRepository;

	// 租用項目資料存取物件
	@Autowired
	private RentalItemRepository rentalItemRepository;


    RentalItemService(ReservationRepository reservationRepository, AppUserRepository appUserRepository) {
        this.reservationRepository = reservationRepository;
        this.appUserRepository = appUserRepository;
    }
	
	
	/* 
	 * 查詢租用項目清單
	 * 可以輸入keyword 與 type
	 * 回傳 : List<RentalItemResponse>
	*/
	public List<RentalItemResponse> findAll(String keyword , String type) {
		boolean hasKeyword = keyword != null && !keyword.isBlank();
		boolean hasType = type != null && !type.isBlank();
		
		List<RentalItem> items;
		if(hasKeyword && hasType) {
			items = rentalItemRepository.findByNameContainingIgnoreCaseAndTypeIgnoreCase(keyword, type);
		}else if (hasKeyword) {
			items = rentalItemRepository.findByNameContainingIgnoreCase(keyword);
		}else {
			items = rentalItemRepository.findAll();
		}
	
		// entity 轉 DTO
		return items.stream().map(RentalItemMapper::toResponse).toList();
		
	}
	
	
	
	/*
	 * 依照租用項目 ID 來查詢單筆資料
	 * 若 ID 不存在會拋出 ResourceNotFoundException
	 * */
	public RentalItemResponse findById(Long id) throws ResourceNotFoundException{
		RentalItem rentalItem = getEntity(id);
		return RentalItemMapper.toResponse(rentalItem);
	}
	
	/*
	 * 新增租用項目
	 * 此方法會將前端傳入的 Request DTO 轉換並複製到新的 RentalItem Entity
	 * 再透過 JPA Respository 寫入到資料庫
	 * 
	 * @Transactional 確保新增流程在同一個交易中完成
	 * 若過程中發生 RuntimeException 資料庫操作會自動回滾
	 * */
	
	@Transactional
	public RentalItemResponse create(RentalItemRequest request) {
		RentalItem item = new RentalItem();
		RentalItemMapper.copyToEntity(request, item);
		// save
		item = rentalItemRepository.save(item);
		// entity 轉 Response DTO
		return RentalItemMapper.toResponse(item);
	}
	
	
	
	
	
	/*
	 * 修改租用項目
	 * 
	 * 此方法會先依照 ID 取得 Entity ， 再將 Request DTO 中的新資料
	 * 複製到該 Entity
	 * 
	 * 由於 Entity 是處於JPA Persistence Content 管理狀態，
	 * 所以當方法結束並移交交易時，會自動執行更新資料庫的動作(不用save)
	 * */
	
	@Transactional
	public RentalItemResponse update(Long id , RentalItemRequest request) throws ResourceNotFoundException {
		RentalItem item = getEntity(id);
		RentalItemMapper.copyToEntity(request, item);
		// 不用 save
		
		return RentalItemMapper.toResponse(item);
	}
	
	
	/*
	 * 刪除指定項目
	 * 
	 * 確認資料存在後就會進行刪除動作
	 * 
	 * */
	@Transactional
	public void delete(Long id) {
		RentalItem item = getEntity(id);
		rentalItemRepository.deleteById(id);
	}
	
	
	
	// 根據 id 取得 RentalItem Entity
		private RentalItem getEntity(long id) {
			return rentalItemRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("找不到租用項目 id= " + id));
		}
}
