package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.YoubikeApplication;
import com.example.demo.model.Bike;
import com.example.demo.response.ApiResponse;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api")//資源分組
public class ApiController {

    private final YoubikeApplication youbikeApplication;

    ApiController(YoubikeApplication youbikeApplication) {
        this.youbikeApplication = youbikeApplication;
    }
	/**
	 * 1. 多筆參數轉 Map
	 * stationName 站名(String),bikeType 車型(String), rentPrice 價格(Double), isAvailable 可供租借(Boolean)
	 * 路徑: /json/bike?stationName=Songshan&bikeType=YouBike2.0&rentPrice=2&isAvailable=true
	 * 路徑: /json/bike?stationName=Songshan&bikeType=YouBike2.0E&rentPrice=5&isAvailable=true
	 * 網址: http://localhost:8080/api/json/bike?stationName=Songshan&bikeType=YouBike2.0&rentPrice=2&isAvailable=true
	 * 網址: http://localhost:8080/api/json/bike?stationName=Songshan&bikeType=YouBike2.0E&rentPrice=5&isAvailable=true
	 * 讓參數自動轉成 key/value 的 Map 集合
	 * */
    @GetMapping(value = "/json/bike" , produces = "application/json;charset=utf-8")
	public ResponseEntity<ApiResponse<Object>> getBikeInfo(@RequestParam Map<String, Object> bikeMap){
		System.out.printf("bikeMap=%s%n" , bikeMap);
		return ResponseEntity.ok(ApiResponse.success("成功", bikeMap));
    }

	
    /**
	 * 2. 多筆參數轉 model
	 * stationName 站名(String),bikeType 車型(String), rentPrice 價格(Double), isAvailable 可供租借(Boolean)
	 * 路徑: /json/bike2?stationName=Songshan&bikeType=YouBike2.0&rentPrice=2&isAvailable=true
	 * 網址: http://localhost:8080/api/json/bike2?stationName=Songshan&bikeType=YouBike2.0&rentPrice=2&isAvailable=true
	 * */
    @GetMapping(value = "/json/bike2" , produces = "application/json;charset=utf-8")
   	public ResponseEntity<ApiResponse<Bike>> getBikeInfo2(Bike bike){
    	bike.setId(1);
   		System.out.printf("bikeMap=%s%n" , bike);
   		return ResponseEntity.ok(ApiResponse.success("成功", bike));
   }
    
    /**
	 * 3.路徑參數
	 * 早期設計風格
	 * 路徑：/json/bike?id=1 得到 id=1 的書
	 * 路徑：/json/bike?id=2 得到 id=2 的書
	 * 
	 * 現代設計風格(REST)
	 * GET / bikes   查詢所有書籍
	 * GET / bikes/1 查詢指定書籍
	 * 路徑：/json/bike/1 得到 id=1 的書
	 * 路徑：/json/bike/2 得到 id=2 的書
	 * 網址: http://localhost:8080/api/json/bike/1
	 * 網址: http://localhost:8080/api/json/bike/2
	 */
    @GetMapping(value = "/json/bike/{id}")
	public ResponseEntity<ApiResponse<Bike>> getBikeById(@PathVariable Integer id){
		//站點庫
    	List<Bike> bikes = List.of(
    			new Bike(1,"松山站","YouBike2.0",2.0,true),
    			new Bike(2,"台北站","YouBike2.0",2.0,true),
    			new Bike(3,"高雄站","YouBike2.0",2.0,true),
    			new Bike(4,"台南站","YouBike2.0",2.0,true));
    	//根據id搜尋該筆書籍
		Optional<Bike> optBike = bikes.stream().filter(bike -> bike.getId().equals(id)).findFirst();
		//判斷是否有找到
		if(optBike.isEmpty()) {
			return ResponseEntity.badRequest().body(ApiResponse.error("查無此書"));
		}
		
		Bike bike = optBike.get();
		return ResponseEntity.ok(ApiResponse.success("查詢成功", bike));
	}
    
    
    /**
	 * Lab
	 * 車庫請參考上面的實作
	 * 得到可租借的站點(isAvailable:true)
	 * 網址: http://localhost:8080/api/json/bike/isAvailable/true
	 * 
	 * 得到已租借的站點(isAvailable:false)
	 * 網址: http://localhost:8080/api/json/bike/isAvailable/false
	 */
	@GetMapping(value = "/json/bike/isAvailable/{isAvailable}")
	public ResponseEntity<ApiResponse<List<Bike>>> getBikeLab(@PathVariable Boolean isAvailable){
		//站庫
		List<Bike> bikes = List.of(
			new Bike(1,"松山站","YouBike2.0",2.0,true),
			new Bike(2,"台北站","YouBike2.0",2.0,false),
			new Bike(3,"高雄站","YouBike2.0",2.0,true),
			new Bike(4,"台南站","YouBike2.0",2.0,false)
		);
		//根據搜尋出版情形顯示該筆書籍
		List<Bike> result = bikes.stream().filter(bike -> bike.getIsAvailable().equals(isAvailable)).toList();
		
		if(result.isEmpty()) {
	        return ResponseEntity.badRequest().body(ApiResponse.error("查無" + (isAvailable?"可租借車輛":"已租借車輛") + "站點資料"));
	    }
		

		return ResponseEntity.ok(ApiResponse.success("查詢成功 " + (isAvailable?"可租借車輛":"已租借車輛"), result));
	}
    
}
