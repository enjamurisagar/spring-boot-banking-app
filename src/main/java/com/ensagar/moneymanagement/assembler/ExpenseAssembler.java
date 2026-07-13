package com.ensagar.moneymanagement.assembler;

import org.springframework.stereotype.Component;

import com.ensagar.moneymanagement.dto.ExpenseDTO;
import com.ensagar.moneymanagement.entity.CategoryEntity;
import com.ensagar.moneymanagement.entity.ExpenseEntity;
import com.ensagar.moneymanagement.entity.ProfileEntity;

@Component
public class ExpenseAssembler {

	public ExpenseEntity toEntity(ExpenseDTO dto, ProfileEntity profile, CategoryEntity category) {
		return ExpenseEntity.builder()
				.name(dto.getName())
				.icon(dto.getIcon())
				.amount(dto.getAmount())
				.date(dto.getDate())
				.profile(profile)
				.category(category)
				.build();
	}
	
	public ExpenseDTO toDTO(ExpenseEntity entity) {
		return ExpenseDTO.builder()
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
