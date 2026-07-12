package com.ensagar.moneymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileDTO {

	private Long id;
	private String fullName;
	private String email;
	private String password;
	private String profileImageUrl;

	private java.time.LocalDateTime createdAt;
	private java.time.LocalDateTime updatedAt;
}
