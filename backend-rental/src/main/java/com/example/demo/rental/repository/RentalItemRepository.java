package com.example.demo.rental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.rental.model.entity.RentalItem;

public interface RentalItemRepository extends JpaRepository<RentalItem, Long>{
	List<RentalItem> findByNameContainingIgnoreCase(String keyword);
	List<RentalItem> findByTypeContainingIgnoreCase(String type);
	List<RentalItem> findByNameContainingIgnoreCaseAndTypeIgnoreCase(String keyword , String type);
}
