package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Bike;

public interface BikeRepository {
	//查找全部站點
	List<Bike> findAllBikes();
	//查找指定站點
	Optional<Bike> getBikeById(Integer id);
	//新增站點
	boolean addBike(Bike bike);
	//修改站點
	boolean updateBike(Integer id, Bike bike);
	//刪除站點
	boolean deleteBike(Integer id);
}
