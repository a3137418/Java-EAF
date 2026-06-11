package com.example.demo.rental.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * JWT 驗證過濾器 - 給 Spring Security 使用
 * 
 * 情境:
 * 前端帶 JWT Token 過來，
 * 這個 Filter 會幫我們確認這個 Token 是不是有效
 * 
 * 驗證步驟:
 * 1.攔截每一次的 HTTP Request
 * 2.檢查 Request Header 裡面有沒有 Authorization 資料
 * 3.判斷 Authorization 是否是Bearer Token 格式
 * 4.從 JWT Token 中取出 username
 * 5.根據 username 查詢使用者資料
 * 6.驗證 Token 是否有效
 * 7.如果 Token 合法有效，就把使用者登入狀態放進 Spring Security Context
 * */


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;
	@Autowired
	private AppUserDetailsService appUserDetailsService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//2.檢查 Request Header 裡面有沒有 Authorization 資料
		String authHeader = request.getHeader("Authorization");
		
		//3.判斷 Authorization 是否是Bearer Token 格式
		if (authHeader == null || !authHeader.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		// ex: Authorization = Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJsZWFybmluZy1wbGF0Zm9ybSIsInN1YiI6InRlYWNoZXIxIiwicm9sZSI6IlJPTEVfVEVBQ0hFUiIsImV4cCI6MTc4MTI0ODg5MH0.PYglOrkRRejle1aSLoBeMo-stRqO_qODzQqIdO27AtY
		String token = authHeader.substring(7);// 位置7 開始才是 token 內容
		
		
		//4.從 JWT Token 中取出 username
		try {
			String username = jwtService.extractUsername(token);
			
			//5.根據 username 查詢使用者資料
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				
				UserDetails userDetails = appUserDetailsService.loadUserByUsername(username);
				
				//6.驗證 Token 是否有效
				if (jwtService.isValid(token, userDetails)) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails,null, userDetails.getAuthorities());
					
					// 設定請求細節
					// WebAuthenticationDetailsSource 會把 Request 資訊放進 Authentication 裡面
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					//7.如果 Token 合法有效，就把使用者登入狀態放進 Spring Security Context
					// 將 Authentication 放進 SecurityContext
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		filterChain.doFilter(request , response);
	}

}
