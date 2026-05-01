package com.example.demo.drink;

import java.time.Period;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.drink.DrinkCategory;
import com.example.demo.model.drink.DrinkDetail;
import com.example.demo.model.drink.DrinkItem;
import com.example.demo.model.drink.Topping;
import com.example.demo.repository.drink.DrinkCategoryRepository;
import com.example.demo.repository.drink.DrinkDetailRepository;
import com.example.demo.repository.drink.DrinkItemRepository;
import com.example.demo.repository.drink.ToppingRepository;

/*
 * 請建立一筆飲料店的資料
 * 
 * 1.建立飲料分類:奶茶類
 * 2.建立二個加料:珍珠, 布丁
 * 3.建立一個飲料商品:奶茶(40元)
 * 4.讓這個飲料商品屬於"奶茶類"
 * 5.讓這個飲料商品可以加"珍珠(5元)"與"布丁(10元)"
 * 6.建立這個飲料商品的詳細資料
 *   熱量:550
 *   建議甜度:半糖
 *   建立冰塊:少冰
 * 7.查詢這個飲料商品總價 => 40 + 5 + 10 = 55 
 * */

@SpringBootTest
public class Test_DrinkShop {
	
	@Autowired
	private DrinkCategoryRepository drinkCategoryRepository;
	
	@Autowired
	private DrinkDetailRepository drinkDetailRepository;
	
	@Autowired
	private DrinkItemRepository drinkItemRepository;
	
	@Autowired
	private ToppingRepository toppingRepository;
	
	
	@Test
	public void add() {
		// 1. 新增飲料分類
		DrinkCategory category = new DrinkCategory();
		category.setName("奶茶類");
		drinkCategoryRepository.save(category);
		
		// 2. 新增加料: 珍珠
		Topping pearl = new Topping();
		pearl.setName("珍珠");
		pearl.setPrice(5);
		toppingRepository.save(pearl);
		
		// 3. 新增加料: 布丁
		Topping pudding = new Topping();
		pudding.setName("布丁");
		pudding.setPrice(10);
		toppingRepository.save(pudding);
		
		// 4. 新增飲品商品
		DrinkItem drinkItem = new DrinkItem();
		drinkItem.setName("奶茶");
		drinkItem.setPrice(40);
		
		// ManyToOne
		// 此飲料商品屬於奶茶類
		drinkItem.setDrinkCategory(category);
		
		// ManyToMany
		drinkItem.setToppings(List.of(pearl, pudding));
		
		drinkItemRepository.save(drinkItem);
		
		// 5. 新增飲料詳細資料
		DrinkDetail detail = new DrinkDetail();
		detail.setCalories(550);
		detail.setSugarSuggestion("半糖");
		detail.setIceSuggestion("少冰");
		
		// OneToOne
		// 設份詳細資料是屬於這個飲料商品
		detail.setDrinkItem(drinkItem);
		
		drinkDetailRepository.save(detail);
		
		System.out.println("Add OK");
	}
	
	
}