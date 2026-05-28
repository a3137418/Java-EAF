package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

/**
 * Spring Security 設定類別
 * - 設定表單登入, 授權規則, 登出行為, 例外處理與用戶資料
 * */

@Configuration // 標註為 Spring 設定類別
@EnableWebSecurity // 啟用 Spring Web Security
public class SecurityConfig {
	
	/**
	 * 定義 Spring Security 過濾器鍊 (安全規則,登入,登出,例外)
	 * */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		// ===== 授權規則設定 =====
		.authorizeHttpRequests(auth -> auth
			// 允許所有人存取登入頁與靜態資源（CSS/JS/圖片）
			.requestMatchers("/","/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
			// /admin/** 僅限 ADMIN 角色
			.requestMatchers("/admin/**").hasRole("ADMIN")
			// /user/** 允許 USER 或 ADMIN 角色
			.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
			// 其他所有請求都需認證
			.anyRequest().authenticated()
		)
		// ===== 表單登入設定 =====
		.formLogin(form -> form
			.loginPage("/login") // 指定自訂登入頁路徑（GET /login）
			.permitAll()         // 允許所有人存取登入頁
		)
		// ===== 登出設定 =====
		.logout(logout -> logout
			.logoutSuccessUrl("/login?logout=true") // 登出成功後導向登入頁
			// 預設 logout 只接受 POST，這裡允許 GET（不建議於生產環境使用）
			.logoutRequestMatcher(PathPatternRequestMatcher.withDefaults()
		            .matcher(HttpMethod.GET, "/logout"))
			.permitAll() // 允許所有人存取登出
		)
		// ===== 例外處理（權限不足）設定 =====
		.exceptionHandling(exception -> exception
			.accessDeniedPage("/accessDenied") // 權限不足時導向 /accessDenied 頁面
		)
		// ===== HSTS 設定 =====
		.headers(headers -> headers
				.httpStrictTransportSecurity(hsts -> hsts
						.maxAgeInSeconds(31536000) // 有效期(一年)
				)
		); 
		
		
	
		return http.build();
	}
	
}