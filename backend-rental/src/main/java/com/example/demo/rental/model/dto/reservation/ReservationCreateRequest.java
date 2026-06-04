package com.example.demo.rental.model.dto.reservation;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationCreateRequest {

	@NotNull(message = "租用項目不可空白")
	private Long itemId;
	
	@NotNull(message = "開始時間不可空白")
	@Future(message = "開始時間不可以小於現在時間")
	private LocalDateTime startTime;
	
	@NotNull(message = "結束時間不可空白")
	@Future(message = "結束時間不可以小於現在時間")
	private LocalDateTime endTime;
	
	private String note;
}
