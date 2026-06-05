package com.example.demo.service;

import java.util.List;

import com.example.demo.exception.BikeException;
import com.example.demo.model.Bike;

public interface BikeService {
	List<Bike> findAllBikes();
	Bike getBikeById(Integer id) throws BikeException;
	void addBike(Bike bike) throws BikeException;
	
	void updateBike(Integer id, Bike bike) throws BikeException;
	void updateBikeName(Integer id, String stationName) throws BikeException;
	void updateBikePrice(Integer id, Double rentPrice) throws BikeException;
	void updateBikeNameAndPrice(Integer id, String stationName , Double rentPrice) throws BikeException;
	
	void deleteBike(Integer id) throws BikeException;
}
