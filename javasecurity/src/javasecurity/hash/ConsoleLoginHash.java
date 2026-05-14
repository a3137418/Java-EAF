package javasecurity.hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

// 利用 Hash 與 Salt 來建立 user 帳戶資料，並且可以進行比對
public class ConsoleLoginHash {
	
	// 儲存使用者的 salt 與 hash
	private static class User{
		String salt;
		String hash;
		
		User(String salt , String hash){
			this.salt = salt;
			this.hash = hash;
		}
	}
	// 產生 salt
	private static String generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] saltByte = new byte[16];
		random.nextBytes(saltByte);
		return Base64.getEncoder().encodeToString(saltByte);
	}
	
	//產生 hash
	// password + salt 後做 SHA-256 雜湊
	private static String hash(String password , String salt) throws Exception{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		String passwordWithSalt = password + salt;
		byte[] hashBytes = md.digest(passwordWithSalt.getBytes(StandardCharsets.UTF_8));
		
		StringBuilder sb = new StringBuilder();
		for(byte b : hashBytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}
	
	//註冊帳號
	private static void register(String username , String password) throws Exception{
		String salt = generateSalt();
		String hash = hash(password, salt);
		// 建立使用者
		users.put(username , new User(salt, hash));
	}
	
	// 登入檢查
	private static boolean login(String username , String password) throws Exception{
		User user =users.get(username);
		
		// 若帳號不存在
		if(user == null) {
			System.err.println("帳號不存在");
			return false;
		}
		
		
		// 使用輸入的密碼 + 原本的 salt 重新雜湊
		String inputHash = hash(password, user.salt);
		
		// 比對雜湊值
		return inputHash.equals(user.hash);
	
	}
	
	// 模擬資料庫
	private static Map<String, User> users = new HashMap<>();
	
	
	
	
	
	public static void main(String[] args) {
		
	}
}
