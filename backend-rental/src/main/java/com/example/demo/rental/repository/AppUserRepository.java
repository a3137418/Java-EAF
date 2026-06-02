package com.example.demo.rental.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.rental.model.entity.AppUser;
import java.util.List;


public interface AppUserRepository extends JpaRepository<AppUser, Long>{
	Optional<AppUser> findByUsername(String username);
	boolean existsByUsername(String username);
}
