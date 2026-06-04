package com.example.demo;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.rental.model.entity.RentalItem;
import com.example.demo.rental.model.enums.ItemStatus;
import com.example.demo.rental.repository.RentalItemRepository;

@SpringBootTest
public class AddRentalItem {
	
	@Autowired
	RentalItemRepository itemRepository;
	
	@Test
	public void add() {
		
		if(itemRepository.count() == 0) {
			
			List<RentalItem> items = List.of(
                    item("A101 多功能教室", "教室", "台北訓練中心 1F", "800", "適合 20 人以下小班教學，含投影機與白板", "https://images.unsplash.com/photo-1524178232363-1fb2b075b655"),
                    item("B202 電腦教室", "教室", "台北訓練中心 2F", "1200", "可容納 30 人，含電腦、投影機、網路", "https://images.unsplash.com/photo-1516321318423-f06f85e504b3"),
                    item("4K 攝影機組", "設備", "器材室", "300", "適合課程錄影與直播使用", "https://images.unsplash.com/photo-1516035069371-29a1b244cc32"),
                    item("無線麥克風組", "設備", "器材室", "150", "含兩支無線麥克風與接收器", "https://images.unsplash.com/photo-1590602847861-f357a9332bbc"),
                    item("大型活動場地", "場地", "活動中心", "2500", "可容納 100 人，適合發表會與成果展", "https://images.unsplash.com/photo-1517457373958-b7bdd4587205")
			);
			
			itemRepository.saveAll(items);
			
			System.out.println("新增可租借項目 OK");
		} else {
			System.out.println("資料庫中已有可租借項目");
		}
		
	}
	
	private RentalItem item(String name, String type, String location, String price,
			String description, String imageUrl
			) {
		
		RentalItem item = new RentalItem();
		item.setName(name);
		item.setType(type);
		item.setLocation(location);
		item.setPricePerHour(new BigDecimal(price));
		item.setStatus(ItemStatus.AVAILABLE);
		item.setDescription(description);
		item.setImageUrl(imageUrl);
		
		return item;
	}
}