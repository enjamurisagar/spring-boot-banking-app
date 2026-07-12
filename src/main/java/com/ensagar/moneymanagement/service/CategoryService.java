package com.ensagar.moneymanagement.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ensagar.moneymanagement.assembler.CategoryAssembler;
import com.ensagar.moneymanagement.dto.CategoryDTO;
import com.ensagar.moneymanagement.entity.CategoryEntity;
import com.ensagar.moneymanagement.entity.ProfileEntity;
import com.ensagar.moneymanagement.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService{

	private final ProfileService profileService;
	private final CategoryRepository categoryRepository;
	private final CategoryAssembler assembler;
	
	public CategoryDTO saveCategory(CategoryDTO dto) {
		ProfileEntity profileEntity = profileService.getCurrentProfile();
		if(categoryRepository.existsByNameAndProfileId(dto.getName(), profileEntity.getId())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Category with this name already exists");
		}
		
		CategoryEntity entity = assembler.toEntity(dto, profileEntity);
		entity = categoryRepository.save(entity);
		
		return assembler.toDTO(entity);
	}
	
}
