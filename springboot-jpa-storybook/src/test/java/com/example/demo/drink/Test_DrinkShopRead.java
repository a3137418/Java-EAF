package com.example.demo.drink;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.drink.DrinkDetail;
import com.example.demo.model.drink.DrinkItem;
import com.example.demo.model.drink.Topping;
import com.example.demo.repository.drink.DrinkCategoryRepository;
import com.example.demo.repository.drink.DrinkDetailRepository;
import com.example.demo.repository.drink.DrinkItemRepository;
import com.example.demo.repository.drink.ToppingRepository;

@SpringBootTest
public class Test_DrinkShopRead {
	
	@Autowired
	private DrinkCategoryRepository drinkCategoryRepository;
	
	@Autowired
	private DrinkDetailRepository drinkDetailRepository;
	
	@Autowired
	private DrinkItemRepository drinkItemRepository;
	
	@Autowired
	private ToppingRepository toppingRepository;
	
	@Test
	@Transactional
	public void read() {
		/*
		 * 題目:
		 * 查詢所有飲料商品並因出
		 * 
		 * 1.飲料名稱
		 * 2.飲料價格
		 * 3.所屬分類
		 * 4.飲料詳細資料:熱量，甜度，冰塊
		 * 5.此飲料可搭配的加料
		 * */
		
		List<DrinkItem> drinkItems = drinkItemRepository.findAll();
		
		drinkItems.forEach(drinkItem ->{
			System.out.printf("飲料名稱: %s%n", drinkItem.getName());
			System.out.printf("飲料價格: %d%n", drinkItem.getPrice());
			
			//ManyToOne 查詢所屬分類
			System.out.printf("飲料分類: %s%n", drinkItem.getDrinkCategory().getName());
			
			//OneToOne 查詢飲料詳細資料
			DrinkDetail detail = drinkItem.getDrinkDetail();
			System.out.printf("\t熱量: %d%n", detail.getCalories());
			System.out.printf("\t甜度: %s%n", detail.getSugarSuggestion());
			System.out.printf("\t冰塊: %s%n", detail.getIceSuggestion());
			
			//ManyToMany
			List<Topping> toppings = drinkItem.getToppings();
			
			toppings.forEach(topping ->{
				System.out.printf("- %s 價格:%d%n" , topping.getName(),topping.getPrice());
			});
		});
		
		
	}
}
