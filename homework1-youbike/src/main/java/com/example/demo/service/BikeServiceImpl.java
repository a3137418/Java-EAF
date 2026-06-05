package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.exception.BikeException;
import com.example.demo.model.Bike;
import com.example.demo.repository.BikeRepository;

@Service //專門負責 邏輯服務 的元件
public class BikeServiceImpl implements BikeService{

	@Autowired  //自動綁定
	@Qualifier("bikeRepositoryImpl") //指定實現類
	private BikeRepository bikeRepository;
	
	@Override
	public List<Bike> findAllBikes() {
		return bikeRepository.findAllBikes();
	}

	@Override
	public Bike getBikeById(Integer id) throws BikeException {
		Optional<Bike> optBike = bikeRepository.getBikeById(id);
		if(optBike.isEmpty()) {
			throw new BikeException("id:" + id + ",查無此筆");
		}
		return optBike.get();
	}

	@Override
	public void addBike(Bike bike) throws BikeException {
		if(!bikeRepository.addBike(bike)) {
			throw new BikeException("新增失敗,"+bike);
		}
	}

	@Override
	public void updateBike(Integer id, Bike bike) throws BikeException {
		if(!bikeRepository.updateBike(id,bike)) {
			throw new BikeException("修改失敗,id:"+id+","+bike);
		}	
	}

	@Override
	public void updateBikeName(Integer id, String stationName) throws BikeException {
		Bike bike = getBikeById(id);
		bike.setStationName(stationName);
		updateBike(bike.getId(), bike);
		
	}

	@Override
	public void updateBikePrice(Integer id, Double rentPrice) throws BikeException {
		Bike bike = getBikeById(id);
		bike.setRentPrice(rentPrice);
		updateBike(bike.getId(), bike);
		
	}

	@Override
	public void updateBikeNameAndPrice(Integer id, String stationName, Double rentPrice) throws BikeException {
		Bike bike = getBikeById(id);
		bike.setStationName(stationName);
		bike.setRentPrice(rentPrice);
		updateBike(bike.getId(), bike);
	}

	@Override
	public void deleteBike(Integer id) throws BikeException {
		if(!bikeRepository.deleteBike(id)) {
			throw new BikeException("刪除失敗,id:"+id);
		}
	}
	
}
