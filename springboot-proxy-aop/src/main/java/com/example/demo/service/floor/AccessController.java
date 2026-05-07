package com.example.demo.service.floor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

// 樓層權限存取控制
@Component
public class AccessController {
	
	// 所有用戶授權樓層資料(In-Memory)
	private static final Map<String, Set<Integer>> userFloorMap = new HashMap<>();
	
	static {
		userFloorMap.put("bob",      Set.of(1, 2));          // 員工 bob 可以到的樓層
		userFloorMap.put("alice",    Set.of(1, 2, 3));       // 員工 alice 可以到的樓層
		userFloorMap.put("guest",    Set.of(1));             // 訪客可以到的樓層
		userFloorMap.put("security", Set.of(1, 2, 3, 4, 5)); // 保全可以到的樓層
	}
	
	public boolean hasAccess(String username, int floor) {
		return userFloorMap.getOrDefault(username, Set.of()).contains(floor);
	}
	
}