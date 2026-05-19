package javasecurity.random;

import java.security.KeyPair;
import java.util.Base64;
import java.util.Scanner;

import javasecurity.util.KeyUtil;

public class TOTP_Login {

	public static void main(String[] args) throws Exception {
		String password = "1234";
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("請輸入密碼:");
		
		boolean check = scanner.next().equals(password);
		
		if(!check) {
			System.out.println("Login Fail");
			return;
		}
		
		System.out.printf("手機看到的驗證碼: %s%n", generateTOTP(password));
		
		System.out.print("請輸入驗證碼:");
		String code = scanner.next();
		if(generateTOTP(password).equals(code)) {
			System.out.println("驗證碼輸入成功, 登入成功 !");
		} else {
			System.err.println("驗證碼輸入失敗, 登入失敗 !");
		}
		
		scanner.close();

	}
	
	public static String generateTOTP(String username) throws Exception {
		// 金鑰(以登入者的名稱當作金鑰)
		String secret = Base64.getEncoder().encodeToString(username.getBytes());
		// 每秒 3 秒換一次
		//long timeInterval = System.currentTimeMillis() / (3 * 1000L);
		// 每秒 10 秒換一次
		long timeInterval = System.currentTimeMillis() / (10 * 1000L);
		// 得到 TOPT 密碼(使用演算法: HMACSHA256)
		String totp = KeyUtil.generateTOTP(secret, timeInterval, "HMACSHA256");
		return totp;
	}

}