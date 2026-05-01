package com.example.demo.repository.drink;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.drink.DrinkItem;

public interface DrinkItemRepository extends JpaRepository<DrinkItem, Integer>{

}
