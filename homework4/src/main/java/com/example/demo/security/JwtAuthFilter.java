package com.example.demo.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	public JwtAuthFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain)
	        throws ServletException, IOException {

	    String authHeader = request.getHeader("Authorization");
	    System.out.println("[JwtAuthFilter] Authorization header: " + authHeader);

	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	        String token = authHeader.substring(7);
	        boolean valid = jwtUtil.isTokenValid(token);
	        System.out.println("[JwtAuthFilter] isTokenValid: " + valid);

	        if (valid) {
	            try {
	                String username = jwtUtil.extractUsername(token);
	                String role = jwtUtil.extractRole(token);
	                System.out.println("[JwtAuthFilter] username=" + username + ", role=" + role);

	                UsernamePasswordAuthenticationToken auth =
	                        new UsernamePasswordAuthenticationToken(
	                                username,
	                                null,
	                                List.of(new SimpleGrantedAuthority(role))
	                        );
	                SecurityContextHolder.getContext().setAuthentication(auth);
	            } catch (Exception e) {
	                System.out.println("[JwtAuthFilter] 角色解析失敗: " + e.getMessage());
	            }
	        }
	    }

	    filterChain.doFilter(request, response);
	}

}
