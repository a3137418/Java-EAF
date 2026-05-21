package javasecurity.jwt;

import java.util.Date;
import java.util.List;

import com.nimbusds.jwt.JWTClaimsSet;

import javasecurity.util.KeyUtil;

/**
 * 帳號密碼正確
 *    ↓
 * 伺服器發一張 JWT 通行證
 *    ↓
 * JWT 裡面寫著
 * 使用者: admin
 * 角色: ADMIN
 * 權限: USER_DELETE
 *    ↓
 * 呼叫刪除會員 API 時
 * 伺服器檢查 JWT 是否有效
 *    ↓
 * 再檢查有沒有 USER_DELETE 權限
 * 
 * */
public class LoginAndDeleteUser {
	public static void main(String[] args) throws Exception {
		// 1. 產生 JWT 簽名密鑰
		String signingSecret = KeyUtil.generateSecret(32);
		
		// 2. 模擬登入(成功之後會得到 token)
		String token = login("admin", "1234", signingSecret);
		if(token == null) {
			System.out.println("登入失敗");
			return;
		}
		
		System.out.printf("登入成功, 取得 JWT: %s%n", token);
		
		System.out.println("\n=======================\n");
		
		// 3. 模擬呼叫刪除會員 API
		deleteUserApi(token, signingSecret);
		
		System.out.println();
		// 4. 模擬呼叫刪除會員 API
		addUserApi(token, signingSecret);
		
		System.out.println();
		// 模擬 10 秒延遲
		for(int i=1;i<=10;i++) {
			Thread.sleep(1000);
			System.out.print(i + " ");
		}
		System.out.println("\n");
		
		// 重新調用 API
		deleteUserApi(token, signingSecret);
		System.out.println();
		addUserApi(token, signingSecret);
	}
	
	// 刪除會員 API
	public static void deleteUserApi(String token, String signingSecret) throws Exception {
		System.out.println("呼叫 API: 刪除會員");
		System.out.println("需要權限: USER_DELETE");
		
		// 驗證 JWT 並取得 payload
		boolean check = true;
		try {
			KeyUtil.verifyJWTSignature(token, signingSecret);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			check = false;
		}
		
		if(!check) {
			System.err.println("JWT 驗證失敗");
			return;
		}
		System.out.println("JWT 驗證成功");
		
		JWTClaimsSet claims = KeyUtil.getClaimsFromToken(token);
		String subject = claims.getSubject();
		String role = claims.getStringClaim("role");
		List<String> permissions = claims.getStringListClaim("permissions");
		
		System.out.printf("使用者: %s%n", subject);
		System.out.printf("角色: %s%n", role);
		System.out.printf("權限: %s%n", permissions);
		
		// 檢查權限是否足夠
		if(permissions.contains("USER_DELETE")) {
			System.out.println("授權成功: 可以刪除會員");
		} else {
			System.err.println("授權失敗: 權限不足, 不可刪除會員");
		}
	}
	
	// 新增會員 API
	public static void addUserApi(String token, String signingSecret) throws Exception {
		System.out.println("呼叫 API: 新增會員");
		System.out.println("需要權限: USER_ADD");
		
		// 驗證 JWT 並取得 payload
		boolean check = true;
		try {
			KeyUtil.verifyJWTSignature(token, signingSecret);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			check = false;
		}
				
		if(!check) {
			System.err.println("JWT 驗證失敗");
			return;
		}
		System.out.println("JWT 驗證成功");
		
		JWTClaimsSet claims = KeyUtil.getClaimsFromToken(token);
		String subject = claims.getSubject();
		String role = claims.getStringClaim("role");
		List<String> permissions = claims.getStringListClaim("permissions");
		
		System.out.printf("使用者: %s%n", subject);
		System.out.printf("角色: %s%n", role);
		System.out.printf("權限: %s%n", permissions);
		
		// 檢查權限是否足夠
		if(permissions.contains("USER_ADD")) {
			System.out.println("授權成功: 可以新增會員");
		} else {
			System.err.println("授權失敗: 權限不足, 不可新增會員");
		}
	}
	
	public static String login(String username, String password, String signingSecret) throws Exception {
		// 帳密判斷
		if(!"admin".equals(username) || !"1234".equals(password)) {
			return null;
		}
		
		// 設定有效時間
		Date now = new Date();
		// JWT 10 秒後過期
		Date expireTime = new Date(now.getTime() + (10 * 1000L));
		
		// 建立 JWT payload
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
				.subject(username)
				.issuer("https://my-system.com")
				.issueTime(now) // 發行時間
				.expirationTime(expireTime) // 過期時間
				.claim("role", "ADMIN")
				//.claim("permission", "USER_DELETE")
				.claim("permissions", List.of("USER_DELETE", "USER_ADD"))
				.build();
		
		return KeyUtil.signJWT(claimsSet, signingSecret);
	}
}