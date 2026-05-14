package javasecurity.hash;

import java.security.MessageDigest;
import java.util.Base64;

// Hash 雜湊
public class SimpleHash {
	public static void main(String[] args) throws Exception{
		// 密碼
		String password = "12345678";
		
		// 建立 SHA-256 雜湊演算法物件
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		
		// 將字串轉 byte[] 後進行雜湊
		byte[] hashBytes = md.digest(password.getBytes());
	
		
		// 將 byte[] 轉 16進位格式的字串
		StringBuilder sb = new StringBuilder();
		for(byte b : hashBytes) {
			sb.append(String.format("%02x", b));
		}
		
		// 雜湊結果
		String hashValue = sb.toString();
		
		System.out.printf("原始密碼: %s%n" , password);
		System.out.printf("雜湊結果: %s%n" , hashValue);
		
		
	}
}
