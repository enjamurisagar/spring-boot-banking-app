package com.ensagar.moneymanagement.assembler;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ensagar.moneymanagement.dto.ProfileDTO;
import com.ensagar.moneymanagement.entity.ProfileEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProfileAssembler {

	private final PasswordEncoder passwordEncoder;
	
	public ProfileEntity toProfileEntity(ProfileDTO requestDTO) {
		
		return ProfileEntity.builder()
		.fullName(requestDTO.getFullName())
		.email(requestDTO.getEmail())
		.password(passwordEncoder.encode(requestDTO.getPassword()))
		.profileImageUrl(requestDTO.getProfileImageUrl())
		.createdAt(requestDTO.getCreatedAt())
		.updatedAt(requestDTO.getUpdatedAt())
		.build();
		
	}

	public ProfileDTO toProfileDTO(ProfileEntity entity) {
		return ProfileDTO.builder()
				.id(entity.getId())
				.fullName(entity.getFullName())
				.email(entity.getEmail())
				.profileImageUrl(entity.getProfileImageUrl())
				.createdAt(entity.getCreatedAt())
				.updatedAt(entity.getUpdatedAt())
				.build();
	}
}
