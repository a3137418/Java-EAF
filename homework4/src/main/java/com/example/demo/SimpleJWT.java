package com.example.demo;

import com.nimbusds.jwt.JWTClaimsSet;

/**
 * 建立一個 JWT 令牌
 * 驗證 JWT 令牌
 * */
public class SimpleJWT {
	public static void main(String[] args) throws Exception {
		// 1. 生成簽名密鑰
		// JWK: 產生簽名用的密鑰(32bytes)
		String signingSecret = KeyUtil.generateSecret(32);
		System.out.printf("密鑰: %s%n", signingSecret);
		
		// 2. 創建 JWT 的聲明(claim)
		// JWT: 這是我們要進行簽名的部分(資料主體)
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.subject("Welcome") // 主題
				.issuer("https://welcome.com") // 發行單位
				.claim("id", "001") // 自訂聲明內容
				.claim("name", "John") // 自訂聲明內容
				.claim("dept", "IT") // 自訂聲明內容
				.claim("floor", "7") // 自訂聲明內容
				.claim("tel", "#212") // 自訂聲明內容
				.build();
		System.out.printf("Payload: %s%n", claimsSet);
		
		// 3. 進行簽名(將 claimSet 進行簽名) 的到 token(JWT)
		String token = KeyUtil.signJWT(claimsSet, signingSecret);
		System.out.printf("Token(JWT): %s%n", token);
				
		//4.驗證 token(JWT)
		boolean check = KeyUtil.verifyJWTSignature(token, signingSecret);
		if (check) {
			System.out.println("驗證成功");
			//讀取 token 中的payload 
			JWTClaimsSet claims = KeyUtil.getClaimsFromToken(token);
			System.out.printf("讀取 subjects: %s%n",claims.getSubject());
			System.out.printf("讀取 issure: %s%n",claims.getIssuer());
			System.out.printf("讀取 id: %s%n",claims.getStringClaim("id"));
			System.out.printf("讀取 name: %s%n",claims.getStringClaim("name"));
			System.out.printf("讀取 dept: %s%n",claims.getStringClaim("dept"));
			System.out.printf("讀取 floor: %s%n",claims.getStringClaim("floor"));
			System.out.printf("讀取 tel: %s%n",claims.getStringClaim("tel"));
		}else {
			System.out.println("驗證失敗");
		}
		
	}
}