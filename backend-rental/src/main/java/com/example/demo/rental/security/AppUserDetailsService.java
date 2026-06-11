package com.example.demo.rental.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.rental.model.entity.AppUser;
import com.example.demo.rental.repository.AppUserRepository;

/*
 *自訂的使用者查詢服務-給 Spring Security 使用的
 *
 * 情境:
 * 當使用者登入時，Spring Security 會呼叫:
 * loadUserByUsername(String username) 方法
 * */

@Service
public class AppUserDetailsService implements UserDetailsService{

	@Autowired
	private AppUserRepository appUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = appUserRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("找不到使用者: " + username));
		return User.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.disabled(!user.isEnabled())
				.roles(user.getRole().name())
				.build();
	}
	
	
	
	
	
}
