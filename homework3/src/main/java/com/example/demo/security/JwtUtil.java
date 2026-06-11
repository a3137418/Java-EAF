package com.example.demo.security;

import com.example.demo.KeyUtil;
import com.nimbusds.jwt.JWTClaimsSet;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long expiration;
	
	// 簽發 JWT ， 將 username 和 role 寫入 payload
	public String generateToken(String username , String role) throws Exception{
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.subject(username)
				.claim("role", role)
				.issuer("learning-platform")
				.expirationTime(new Date(System.currentTimeMillis() + expiration))
				.build();
		return KeyUtil.signJWT(claimsSet, secret);
	}
	
	
	// 從 Token 取出帳號
	public String extractUsername(String token) throws Exception{
		return KeyUtil.getClaimsFromToken(token).getSubject();
	}

	// 從 Token 取出角色
	public String extractRole(String token) throws Exception{
		return KeyUtil.getClaimsFromToken(token).getStringClaim("role");
	}
	
	
	
	// 驗證 Token 是否有效（簽名正確且未過期)
	public boolean isTokenValid(String token) {
		try {
			return KeyUtil.verifyJWTSignature(token, secret);
		} catch (Exception e) {
			return false;
		}
	}
	
	
	
	
	
}
