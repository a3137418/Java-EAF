package javasecurity.mac;

import javax.crypto.SecretKey;

import javasecurity.util.KeyUtil;

//訊息驗證碼
public class MACExample {
	public static void main(String[] args) throws Exception {
		// 1.定義通報資料
		String message = "海嘯發布警報";
		System.out.println("=== 通報資料 ===");
		System.out.println(message);
		
		// 2.產生一把專用於 mac 的密鑰
		SecretKey macKey = KeyUtil.generateKeyForHmac();
		
		// 3.利用 macKey 密鑰 + 訊息(message) 生成 MAC 值
		byte[] macValue = KeyUtil.generateMac(macKey, message);
		
		// 4.將 macValue 轉 16 進位字串並印出
		String macHexValue = KeyUtil.bytesToHex(macValue);
		System.out.println("\n=== MAC(Hex) ===");
		System.out.printf(macHexValue);
		
		// 5.在實際應用中，接收方會收到訊息(message)與macHexValue
		// 此時 message 要與 macKey(雙方都要有的密鑰) 產生出 computedMacHexValue 值進行與 macHexValue 的比對
		String receiveMessage = message; // 收到資料
		byte[] computedMacValue = KeyUtil.generateMac(macKey, receiveMessage);
		String computedMacHexValue = KeyUtil.bytesToHex(computedMacValue);
		System.out.println("\n=== computed MAC(Hex) ===");
		System.out.println(computedMacHexValue);
		
		// 6.比較macHexValue 與 computedMacValue
		System.out.println("\n=== 驗證MAC ===");
		if (macHexValue.equals(computedMacHexValue)) {
			System.out.println("來源正確");
		}else {
			System.err.println("來源有誤");
		}
		
		
		
	}
}
