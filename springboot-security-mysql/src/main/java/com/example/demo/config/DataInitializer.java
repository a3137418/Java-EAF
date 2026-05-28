package com.example.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.entity.AppUser;
import com.example.demo.repository.AppUserRepository;

@Configuration
public class DataInitializer {

    private final AppUserRepository appUserRepository;


    DataInitializer(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
	@Bean
	public CommandLineRunner initAdmin(AppUserRepository appUserRepository , PasswordEncoder passwordEncoder) {
		return args ->{
			if(!appUserRepository.existsByUsername("admin")) {
				AppUser admin = new AppUser();
				admin.setUsername("admin");
				admin.setPassword(passwordEncoder.encode("5678"));
				admin.setRole("ADMIN");
				admin.setEnabled(true);
				
				appUserRepository.save(admin);
			}
		};
	}
}
