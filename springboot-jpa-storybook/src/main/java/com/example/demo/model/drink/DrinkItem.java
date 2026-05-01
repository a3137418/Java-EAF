package com.example.demo.model.drink;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
//@Table(name = "drink_item")// 在資料庫裏面的名稱
public class DrinkItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	// 飲料名稱: 珍珠奶茶、拿鐵咖啡、柳橙汁
	@Column(length = 50 , nullable = false)
	private String name;

	// 飲料基本價格
	@Column(nullable = false)
	 private Integer price;
	
	@ManyToOne
	@JoinColumn(name = "drink_category_id")
	private DrinkCategory drinkCategory;
	
	@OneToOne(mappedBy = "drinkItem")
	private DrinkDetail drinkDetail;
	
	@ManyToMany
	@JoinTable(
			name = "drink_item_topping",
			joinColumns = @JoinColumn(name= "drink_item_id"),
			inverseJoinColumns = @JoinColumn(name = "topping_id")
			)
	private List<Topping> toppings;
	
	public void addTopping(Topping topping) {
		if (toppings == null) {
			toppings = new CopyOnWriteArrayList<>();
		}
		toppings.add(topping);
	}
	
	
}
