package com.example.demo.model.drink;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class DrinkCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	// 飲料分類名稱:茶類，咖啡類，果汁類
	@Column(length = 50 , nullable = false)
	private String name;
	
	@OneToMany(mappedBy = "drinkCategory")
	private List<DrinkItem> drinkItems;
}
