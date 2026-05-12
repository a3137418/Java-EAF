package javasecurity.encryption;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

// 建立 AES 金鑰實體檔
// 檔名: aes_key.key
public class GeneratedAESKeyToFile {
	// 建立一個 AES Key (256bits, 32bytes)
	private static final String KEY = "012345678901234567890123456789AB"; // 32 個字
	
	//金鑰存放位置
	private static final String KEY_FILE_PATH = "aes_key.key";
	
	
	
	public static  void main(String[] args) throws Exception{
		SecretKeySpec aesKeySpec = new SecretKeySpec(KEY.getBytes(), "AES");
		
		//保存金鑰
		String keyBase64 = Base64.getEncoder().encodeToString(aesKeySpec.getEncoded());
		Files.writeString(Path.of(KEY_FILE_PATH), keyBase64, StandardOpenOption.CREATE , StandardOpenOption.TRUNCATE_EXISTING);
		System.out.println("金鑰保存成功");
	}
	
}
