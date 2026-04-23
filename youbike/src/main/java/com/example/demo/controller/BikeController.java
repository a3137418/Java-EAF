package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * BookController
 * ============================================
 * 📘 功能說明：
 * 本類別是「書籍管理系統」的 RESTful API 控制器，
 * 負責接收前端 (例如 React / Vue) 的 HTTP 請求，
 * 並呼叫 Service 層進行商業邏輯處理，最後回傳 JSON 結果。
 *
 * --------------------------------------------
 * 📌 使用技術：
 * - Spring Boot (REST API)
 * - ResponseEntity (控制 HTTP 狀態碼)
 * - ApiResponse (統一回傳格式)
 *
 * --------------------------------------------
 * 📌 RESTful API 設計概念：
 *
 * 方法        路徑            功能
 * --------------------------------------------
 * GET         /bike          查詢全部資料
 * GET         /bike/{id}     查詢單一資料
 * POST        /bike          新增資料
 * DELETE      /bike/{id}     刪除資料
 * PUT         /bike/{id}     更新資料 (完整更新)
 * PATCH       /bike/{id}     部分更新 (name + price)
 * PATCH       /bike/stationName/{id}   只改名稱
 * PATCH       /bike/rentPrice/{id}  只改價格
 *
 * --------------------------------------------
 * 📌 分層架構 (MVC)：
 *
 * Controller → 接收請求 (HTTP)
 * Service    → 商業邏輯
 * Repository → 資料庫操作
 *
 * --------------------------------------------
 * 📌 設計重點：
 *
 * 1️⃣ 使用 ResponseEntity
 *   - 可控制 HTTP Status (200, 400, 404...)
 *
 * 2️⃣ 使用 ApiResponse
 *   - 統一 JSON 回傳格式
 *   {
 *     "message": "...",
 *     "data": ...
 *   }
 *
 * 3️⃣ 例外處理 (Exception Handling)
 *   - 捕捉 BookException
 *   - 避免系統直接 crash
 *
 * 4️⃣ RESTful 設計
 *   - 使用 HTTP Method 表達行為
 *   - URL 表示資源 (book)
 *
 * --------------------------------------------
 * 📌 前端呼叫範例：
 *
 * GET    http://localhost:8080/bike
 * POST   http://localhost:8080/bike
 * PUT    http://localhost:8080/bike/1
 * PATCH  http://localhost:8080/bike/price/1
 * DELETE http://localhost:8080/bike/1
 *
 * --------------------------------------------
 * 📌 跨域設定：
 * @CrossOrigin 允許前端 (localhost:5173) 呼叫 API
 * (常見於 React / Vite 開發環境)
 *
 * ============================================
 */

import com.example.demo.exception.BikeException;
import com.example.demo.model.Bike;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.BikeService;

import jakarta.websocket.server.PathParam;
@CrossOrigin(origins = {"http://localhost:5173"}) //允許跨域請求給 react 使用
@RestController
@RequestMapping("/bike")
public class BikeController {
	@Autowired
	@Qualifier("bikeServiceImpl") //若該 interface 只有一個實現類，則 @Qualifier 可以省略
	private BikeService bikeService;
	//GET /bike 查詢全部資料
	@GetMapping
	public ResponseEntity<ApiResponse<List<Bike>>> findAllBikes() {
		List<Bike> bikes = bikeService.findAllBikes();
		if(bikes.size() == 0) {
			return ResponseEntity.badRequest().body(ApiResponse.error("查無任何資料"));
		}
		return ResponseEntity.ok(ApiResponse.success("查詢成功", bikes));
	}
	
	//GET /bike 查詢單一資料
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Bike>> getBikeById(@PathVariable Integer id){
		Bike bike = bikeService.getBikeById(id);
		return ResponseEntity.ok(ApiResponse.success("查詢成功", bike));
		
	}
	
	//POST /bike     新增資料
	@PostMapping
	public ResponseEntity<ApiResponse<Bike>> addBike(@RequestBody Bike bike){
		bikeService.addBike(bike);
		return ResponseEntity.ok(ApiResponse.success("新增成功", bike));
	}
	
	//DELETE      /bike/{id}     刪除資料
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>> deleteBike(@PathVariable Integer id){
		bikeService.deleteBike(id);
		return ResponseEntity.ok(ApiResponse.success("刪除成功", "id:"+ id));
	}
	
	//PUT         /bike/{id}     更新資料 (完整更新)
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<Bike>> updateBike(@PathVariable Integer id, @RequestBody Bike bike){
		//修改
		bikeService.updateBike(id, bike);
		//重查
		bike = bikeService.getBikeById(id);
		return ResponseEntity.ok(ApiResponse.success("修改成功", bike));
		
	}
	
	//PATCH /bike/{id}     部分更新 (name + price)
	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponse<Bike>> updateNameAndPrice(@PathVariable Integer id , @RequestBody Bike bike){
		//修改
		bikeService.updateBikeNameAndPrice(id, bike.getStationName(), bike.getRentPrice());
		//重查
		bike = bikeService.getBikeById(id);
		return ResponseEntity.ok(ApiResponse.success("修改站名與價格成功", bike));
	}
	
	
	//PATCH /bike/stationName/{id}   只改名稱
	@PatchMapping("/stationName/{id}")
	public ResponseEntity<ApiResponse<Bike>> updateName(@PathVariable Integer id, @RequestBody Bike bike){
		//修改
		bikeService.updateBikeName(id, bike.getStationName());
		//重查
		bike = bikeService.getBikeById(id);
		return ResponseEntity.ok(ApiResponse.success("修改站名成功", bike));
	}
	
	//PATCH /bike/rentPrice/{id}  只改價格
	@PatchMapping("/rentPrice/{id}")
	public ResponseEntity<ApiResponse<Bike>> updatePrice(@PathVariable Integer id, @RequestBody Bike bike){
		//修改
		bikeService.updateBikePrice(id, bike.getRentPrice());
		//重查
		bike = bikeService.getBikeById(id);
		return ResponseEntity.ok(ApiResponse.success("修改價格成功", bike));
	}
}
