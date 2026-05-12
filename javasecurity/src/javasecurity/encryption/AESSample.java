package javasecurity.encryption;

import java.util.Arrays;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import javasecurity.util.KeyUtil;

// AES 對稱式加密
public class AESSample {
	
	// 建立一個 AES Key (256bits, 32bytes)
	private static final String KEY = "012345678901234567890123456789AB"; // 32 個字
	
	public static void main(String[] args) throws Exception {
		String originalText = "明早7:30要招開人事緊急會議";
		System.out.printf("原始明文: %s%n", originalText);
		System.out.println("------------------------------");
		
		/**
		 * 利用 AES 進行加密
		 * 加密流程:明文 -> 加密(encryptedECB) -> 編碼(Base64)
		 * 解密流程:解碼(Base64) -> 解密(decryptedECB) -> 明文
		 * */
		
		// 1. 建立 AES 密鑰規範
		SecretKey aesKeySpec = new SecretKeySpec(KEY.getBytes(), "AES"); // 金鑰
		
		// 2. 選擇 ECB 模式進行對明文的加密(透過金鑰加密)
		byte[] encryptedECB = KeyUtil.encryptWithAESKey(aesKeySpec, originalText);
		System.out.printf("加密後: %s%n", Arrays.toString(encryptedECB));
		
		// 3. 透過 Base64 編碼以利傳輸
		String encoderECBBase64 = Base64.getEncoder().encodeToString(encryptedECB);
		System.out.printf("編碼後: %s%n", encoderECBBase64);
		System.out.println("------------------------------");
		
		// 4.解碼
		byte[] decoderECBBase64 =  Base64.getDecoder().decode(encoderECBBase64);
		System.out.printf("解碼後: %s%n" , Arrays.toString(decoderECBBase64));
		
		
		// 5.解密
		String decyptedECB = KeyUtil.decryptWithAESKey(aesKeySpec, decoderECBBase64);
		System.out.printf("解密後: %s%n" , decyptedECB);
		
		
	}
}