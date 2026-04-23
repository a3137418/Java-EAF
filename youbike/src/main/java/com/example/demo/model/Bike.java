package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bike {
	private Integer id;
	private String stationName;//站點名稱
	private String bikeType;//車型
	private Double rentPrice;//租借費用
	private Boolean isAvailable = false;//目前是否可租借
}
