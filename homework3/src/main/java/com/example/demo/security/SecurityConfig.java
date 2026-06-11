package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthFilter jwtAuthFilter;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
			.csrf(csrf -> csrf.disable())
			.sessionManagement(session ->
					session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
					//公開端點
					.requestMatchers("/api/auth/**").permitAll()
					.requestMatchers("/" , "/index.html" , "/static/**").permitAll()
					// 管理員專屬
					.requestMatchers(HttpMethod.GET , "/api/users").hasAuthority("ROLE_ADMIN")
					.requestMatchers(HttpMethod.PUT , "/api/users/*/status").hasAuthority("ROLE_ADMIN")
					.requestMatchers(HttpMethod.POST , "/api/users/*/approve").hasAuthority("ROLE_ADMIN")
					// 講師專屬
					.requestMatchers(HttpMethod.POST , "/api/courses").hasAuthority("ROLE_TEACHER")
					.requestMatchers(HttpMethod.GET , "/api/teachers/*/students").hasAuthority("ROLE_TEACHER")
					// 學生專屬
					.requestMatchers(HttpMethod.POST , "/api/students/*/enroll/*").hasAuthority("ROLE_STUDENT")
					// 其餘需登入
					.anyRequest().authenticated()
			)
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
