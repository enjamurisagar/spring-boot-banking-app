package com.ensagar.moneymanagement.assembler;

import org.springframework.stereotype.Component;

import com.ensagar.moneymanagement.dto.IncomeDTO;
import com.ensagar.moneymanagement.entity.CategoryEntity;
import com.ensagar.moneymanagement.entity.IncomeEntity;
import com.ensagar.moneymanagement.entity.ProfileEntity;

@Component
public class IncomeAssembler {

	public IncomeEntity toEntity(IncomeDTO dto, ProfileEntity profile, CategoryEntity category) {
		return IncomeEntity.builder()
				.name(dto.getName())
				.icon(dto.getIcon())
				.amount(dto.getAmount())
				.date(dto.getDate())
				.profile(profile)
				.category(category)
				.build();
	}
	
	public IncomeDTO toDTO(IncomeEntity entity) {
		return IncomeDTO.builder()
				.id(entity.getId())
				.name(entity.getName())
				.icon(entity.getIcon())
				.amount(entity.getAmount())
				.date(entity.getDate())
				.categoryId(entity.getCategory() != null ? entity.getCategory().getId() : null)
				.createdAt(entity.getCreatedAt())
				.updatedAt(entity.getUpdatedAt())
				.build();
	}
}
