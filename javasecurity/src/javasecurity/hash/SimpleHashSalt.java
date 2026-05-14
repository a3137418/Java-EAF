package javasecurity.hash;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

// Hash 雜湊 + 鹽(salt)
public class SimpleHashSalt {
	public static void main(String[] args) throws Exception {
		// 密碼
		String password = "12345678";
		
		// 產生 salt
		SecureRandom random = new SecureRandom();
		byte[] saltBytes = new byte[16];
		random.nextBytes(saltBytes);
		
		// 將 salt 轉 base64 字串
		String salt = Base64.getEncoder().encodeToString(saltBytes);
		
		// 將原本的 password 加入鹽
		String passwordWithSalt = password + salt;
		
		// 建立 SHA-256 雜湊演算法物件
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		
		// 將已經加入鹽的密碼字串轉 byte[] 後進行雜湊
		byte[] hashBytes = md.digest(passwordWithSalt.getBytes());
		
		// 將 byte[] 轉 16 進位格式的字串
		StringBuilder sb = new StringBuilder();
		for(byte b : hashBytes) {
			sb.append(String.format("%02x", b));
		}
		
		// 雜湊結果
		String hashValue = sb.toString();
		
		System.out.printf("原始密碼: %s%n", password);
		System.out.printf("鹽 Salt: %s%n", salt);
		System.out.printf("密碼 + Salt: %s%n", passwordWithSalt);
		System.out.printf("雜湊結果: %s%n", hashValue);
		
	}
}