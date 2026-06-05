package com.example.demo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	
	private String email;
	
	// 加上 cascade 和 orphanRemoval 是為了滿足作業級聯刪除的需求
	@OneToOne(mappedBy = "user" , cascade = CascadeType.ALL , orphanRemoval = true)
	private UserProfile userProfile;
	
}
