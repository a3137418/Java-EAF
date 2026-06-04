package com.example.demo.rental.mapper;

import com.example.demo.rental.model.dto.auth.UserProfileDto;
import com.example.demo.rental.model.entity.AppUser;

public class UserMapper {
	
	public static UserProfileDto toProfileDto(AppUser user) {
		return new UserProfileDto(
				user.getId(), 
				user.getUsername(), 
				user.getFullName(), 
				user.getPhone(), 
				user.getRole().name());
	}
	
}