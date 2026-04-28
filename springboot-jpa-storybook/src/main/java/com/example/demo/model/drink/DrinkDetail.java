package com.example.demo.model.drink;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class DrinkDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	//熱量:450
	@Column(nullable = true)
	private Integer calories;
	
	//建議甜度:半糖、微糖、無糖
	@Column(length = 50 , nullable = true)
	private String sugarSuggestion;
	
	//建議冰塊:正常冰、少冰、去冰
	@Column(length = 50 , nullable = true)
	private String iceSuggestion;
	
	@OneToOne
	@JoinColumn(name = "drink_item_id")
	private DrinkItem drinkItem;
	
	
}
