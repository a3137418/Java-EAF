package com.example.demo.rental.model.entity;



import com.example.demo.rental.model.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "app_user")
@Getter
@Setter
@NoArgsConstructor
public class AppUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false , unique = true , length = 50)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(name = "full_name" , nullable = false , length = 80)
	private String fullName;
	
	@Column(length = 30)
	private String phone;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false , length = 20)
	private Role role = Role.USER;
	
	@Column(nullable = false)
	private boolean enabled = true;
	
	
}
