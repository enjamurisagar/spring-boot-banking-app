package com.ensagar.moneymanagement.assembler;

import org.springframework.stereotype.Component;

import com.ensagar.moneymanagement.dto.CategoryDTO;
import com.ensagar.moneymanagement.entity.CategoryEntity;
import com.ensagar.moneymanagement.entity.ProfileEntity;

@Component
public class CategoryAssembler {

	public CategoryEntity toEntity(CategoryDTO dto, ProfileEntity profileEntity) {
		return CategoryEntity.builder()
				.name(dto.getName())
				.icon(dto.getIcon())
				.profile(profileEntity)
				.type(dto.getType())
				.build();
	}
	
	public CategoryDTO toDTO(CategoryEntity entity) {
		return CategoryDTO.builder()
				.id(entity.getId())
				.name(entity.getName())
				.icon(entity.getIcon())
				.profileId(entity.getProfile() != null ? entity.getProfile().getId() : null)
				.createdAt(entity.getCreatedAt())
				.updatedAt(entity.getUpdatedAt())
				.type(entity.getType())
				.build();
	}
}
