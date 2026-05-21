package javasecurity.jwt;

import com.nimbusds.jwt.JWTClaimsSet;

import javasecurity.util.KeyUtil;

/*
 * 建立一個 JWT 令牌
 * 驗證JWT令牌
 * */
public class SimpleJWT {
	public static void main(String[] args) throws Exception {
		// 1. 生成簽名密鑰
		// JWK: 產生簽名用的密鑰(32 bytes)
		String signinSecret = KeyUtil.generateSecret(32);
		System.out.printf("密鑰: %s%n" , signinSecret);
		
		// 2.創建 JWT 的聲明(claim)
		// JWT: 這是我們要進行簽名的部分(資料主體)
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.subject("iot") // 主題
				.issuer("htpp://iot.com") //發行單位
				.claim("id", "001") // 自訂聲明內容
				.claim("name", "John")
				.claim("dept", "IT")
				.claim("floor", "7")
				.claim("tel", "#212")
				.build();
		System.out.printf("Payload: %s%n" , claimsSet);
		
		// 3.進行簽名(將 claimSet 進行簽名) 得到 token(JWT)
		String token = KeyUtil.signJWT(claimsSet, signinSecret);
		System.out.printf("Token(JWT):　%s%n" , token);
		
		
		System.out.println("\n=================================");
		
		// 4.驗證 token(JWT)
		boolean check = KeyUtil.verifyJWTSignature(token, signinSecret);
		
		if(check) {
			System.out.println("驗證成功");
			// 讀取 token 中的 Poyload
			JWTClaimsSet claims = KeyUtil.getClaimsFromToken(token);
			System.out.printf("讀取subject: %s%n" , claims.getSubject());
			System.out.printf("讀取issure: %s%n" , claims.getIssuer());
			System.out.printf("讀取id: %s%n" , claims.getStringClaim("id"));
			System.out.printf("讀取name: %s%n" , claims.getStringClaim("name"));
			System.out.printf("讀取dept: %s%n" , claims.getStringClaim("dept"));
			System.out.printf("讀取floor: %s%n" , claims.getStringClaim("floor"));
			System.out.printf("讀取tel: %s%n" , claims.getStringClaim("tel"));
		}else {
			System.out.println("驗證失敗");
		}
		
		
		
	}
}
