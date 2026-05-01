package com.example.demo.repository.drink;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.drink.DrinkCategory;

public interface DrinkCategoryRepository extends JpaRepository<DrinkCategory, Integer>{

}
