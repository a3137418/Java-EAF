package javasecurity.mac;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.crypto.SecretKey;

import javasecurity.util.KeyUtil;

/*
 * 員工已知或得到的資訊
 * 薪資檔案位置   : src/javasecurity/mac/my_salary.txt
 * macKey檔案位置: src/javasecurity/mac/macKey.key
 * HR 發佈的 macHexValue:1e35d4182d8efac6f67f82d2727f7ea393981b02291debc871c740911f3ecb4f
 * 
 * 如何得知 my_salary.txt 是由 HR 所發佈的 ? (來源端確認)
 * */
public class SalaryProtectionVerify {
	
	public static void main(String[] args) throws Exception {
		String filePath = "src/javasecurity/mac/my_salary.txt";
		String keyPath = "src/javasecurity/mac/macKey.key";
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("請輸入 HR 發佈的 macHexValue: ");
		String macHexValueFromHR = scanner.nextLine();
		
		SecretKey macKey = KeyUtil.getSecretKeyFromFile("HmacSHA256", keyPath);
		// 生成要進行比對的 value
		String computedMacHexValue = KeyUtil.generateMac("HmacSHA256", macKey, filePath);
		
		// 驗證 HR 所發佈的 macHexValueFromHR 是否等於 自行算出 computedMacHexValue
		if(macHexValueFromHR.equals(computedMacHexValue)) {
			System.out.println("驗證成功, 資料是來自於 HR");
			// 讀取檔案內容
			System.out.println(Files.readString(Paths.get(filePath)));
		} else {
			System.out.println("驗證失敗 !");
		}
		
		scanner.close();
		
	}
	
}