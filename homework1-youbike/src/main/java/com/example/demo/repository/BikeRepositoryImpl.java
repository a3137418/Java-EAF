package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Bike;

@Repository //專門負責資料存取的元件，Spring 會自動建立該物件並管理
public class BikeRepositoryImpl  implements BikeRepository{
	//InMemory 版
	private List<Bike> bikes = new CopyOnWriteArrayList<>();
	
	//初始資料有4筆
	{
		bikes.add(new Bike(1,"松山站","一般車",2.0,true));
		bikes.add(new Bike(2,"台北站","電輔車",15.0,true));
		bikes.add(new Bike(3,"高雄站","一般車",5.0,false));
		bikes.add(new Bike(4,"台南站","電輔車",20.0,false));
	}
	
	
	@Override
	public List<Bike> findAllBikes() {
		return bikes;
	}

	@Override
	public Optional<Bike> getBikeById(Integer id) {
		return bikes.stream().filter(bike -> bike.getId().equals(id)).findFirst();
	}

	@Override
	public boolean addBike(Bike bike) {
		// 建立newId
		OptionalInt optMaxId = bikes.stream().mapToInt(Bike::getId).max();//取出目前bikes中id的最大值
		int newId = optMaxId.isEmpty() ? 1 : optMaxId.getAsInt() +1;
		//將newId設定給bike
		bike.setId(newId);
		return bikes.add(bike);
	}

	@Override
	public boolean updateBike(Integer id, Bike bike) {
		//透過id找到要修改的資料
		Optional<Bike> optBike = getBikeById(id);
		if(optBike.isEmpty()) {
			return false;
		}
		
		//得到要修改的資料
		Bike orginalBike = optBike.get();
		//更新欄位資料
		Optional.ofNullable(bike.getStationName()).ifPresent(orginalBike::setStationName);
		Optional.ofNullable(bike.getBikeType()).ifPresent(orginalBike::setBikeType);
		Optional.ofNullable(bike.getRentPrice()).ifPresent(orginalBike::setRentPrice);
		Optional.ofNullable(bike.getIsAvailable()).ifPresent(orginalBike::setIsAvailable);
		
//		if(bike.getStationName() != null) orginalBike.setStationName(bike.getStationName());
//		if(bike.getBikeType() != null) orginalBike.setBikeType(bike.getBikeType());
//		if(bike.getRentPrice() != null) orginalBike.setRentPrice(bike.getRentPrice());
//		if(bike.getIsAvailable() != null) orginalBike.setIsAvailable(bike.getIsAvailable());
		
		return true;
	}

	@Override
	public boolean deleteBike(Integer id) {
		//透過id找到要刪除的資料
		Optional<Bike> optBike = getBikeById(id);
		if(optBike.isEmpty()) {
			return false;
		}
		//刪除資料
		return bikes.remove(optBike.get());
	}
	
}
