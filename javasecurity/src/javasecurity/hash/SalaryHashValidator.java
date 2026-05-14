package javasecurity.hash;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javasecurity.util.KeyUtil;

//利用檔案雜湊值驗證資料完整性，確認薪資檔案是否被竄改
public class SalaryHashValidator {
	public static void main(String[] args) throws IOException {
		// 讀檔-薪資檔案路徑
		String filePath = "src/javasecurity/hash/my_salary.txt";
		
		//讀取內容
		String content = Files.readString(Path.of(filePath));
		System.out.println("=== 目前薪資檔案內容===");
		System.out.println(content);
		
		// 原始 hash 檔案路徑
		String hashFilePath = "src/javasecurity/hash/my_salary_hash.txt";
		
		// 讀取原本儲存的 hash 值
		// trim() 用來移除換行符號與空白，避免比對失敗
		String salaryHash = Files.readString(Path.of(hashFilePath)).trim();
		System.out.println("\n=== 原始 hash ===");
		System.out.println(salaryHash);
		
		
		// 重新針對目前薪資檔案計算 hash
		String newSalaryHash = KeyUtil.generateFileHash(filePath);
		System.out.println("\n=== 目前檔案重新計算後的 hash ===");
		System.out.println(newSalaryHash);
		
		// 比對原始 hash 與 new hash 
		System.out.println("\n===驗證結果");
		if (salaryHash.equals(newSalaryHash)) {
			System.out.println("資料正確，檔案未被竄改");
		}else {
			System.err.println("資料異常，檔案可能已被竄改");
		}
		
		
		
		
		
	}

}
