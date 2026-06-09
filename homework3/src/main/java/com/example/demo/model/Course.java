package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "courses")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "課程名稱不可空白")
	private String title;

	@NotNull(message = "售價不可空白")
	@Positive(message = "售價必須大於 0")
	private Double price;

	@ManyToOne
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;

	// 反向關聯，由 User 側的 @JoinTable 管理；排除以防 Lombok @Data 循環引用
	@ManyToMany(mappedBy = "courses")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<User> enrolledUsers = new ArrayList<>();
}
