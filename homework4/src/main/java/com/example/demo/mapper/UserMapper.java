package com.example.demo.mapper;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;

public class UserMapper {

	public static UserDTO toProfileDTO(User user) {
		return new UserDTO(
				user.getId(),
				user.getUsername(),
				user.getEmail(),
				user.getRole(),
				user.isPaid(),
				user.isEnabled());
				
	}
}
