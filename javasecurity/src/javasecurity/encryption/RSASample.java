package javasecurity.encryption;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Base64;

import javasecurity.util.KeyUtil;

// 非對稱式加密
public class RSASample {

	public static void main(String[] args) throws Exception{
		String originalText = "明早7:30要招開人事緊急會議";
		System.out.printf("1. 原始明文: %s%n" , originalText);
		System.out.println("------------------------------");
		
		// 1.生成RSA 密鑰對(公私鑰)
		KeyPair keyPair = KeyUtil.generateRSAKeyPair(); // RSA-2048
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		
		// 2.加密 - 利用公鑰進行加密
		byte[] encryptedBytes = KeyUtil.encryptWithPublicKey(publicKey, originalText.getBytes());
		System.out.printf("2. 加密訊息: %s%n", Arrays.toString(encryptedBytes));
		
		// 3. 編碼 - 透過 Base64 編碼以利傳輸
		String encoderRSABase64 =Base64.getEncoder().encodeToString(encryptedBytes);
		System.out.printf("3. 編碼後: %s%n" , encoderRSABase64);
		System.out.println("------------------------------");
		
		// 4.解碼
		byte[] decoderRSABase64 =  Base64.getDecoder().decode(encoderRSABase64);
		System.out.printf("解碼後: %s%n" , Arrays.toString(decoderRSABase64));
		
		
		// 5.解密 - 利用私鑰解密
		byte[] decyptedRSA = KeyUtil.decryptWithPrivateKey(privateKey, decoderRSABase64);
		String message = new String(decyptedRSA);
		System.out.printf("解密後: %s%n" , message);
		
		
	}
}
