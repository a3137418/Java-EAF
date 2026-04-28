package com.example.demo.model.drink;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity
public class Topping {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	//加料名稱:珍珠、椰果、布丁
	@Column(length = 50 , nullable = true)
	private String name;
	
	//加料價格
	@Column(length = 50 , nullable = false)
	private Integer price;
	
	@ManyToMany(mappedBy = "toppings")
	private List<DrinkItem> drinkItems;

}
