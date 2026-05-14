package javasecurity.hash;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import javasecurity.util.KeyUtil;


// 針對指定檔案產生 hash
public class SalaryHashGenerator {
	public static void main(String[] args) throws IOException {
		// 讀取檔案
		String filePath = "src/javasecurity/hash/my_salary.txt";
		System.out.println(filePath);
		System.out.println(Files.readString(Path.of(filePath)));
		
		System.out.println("\n== 產生 hash ===");
		String salaryHash = KeyUtil.generateFileHash(filePath);
		System.out.printf("Hash: %s%n" , salaryHash);
		
		System.out.println("\n=== 將 hash 存檔 ===");
		String hashFilePath = "src/javasecurity/hash/my_salary_hash.txt";
		Files.writeString(Path.of(hashFilePath), salaryHash , StandardOpenOption.CREATE , StandardOpenOption.TRUNCATE_EXISTING);
		System.out.println(hashFilePath);
		
	}
}
