package com.ensagar.moneymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ensagar.moneymanagement.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>{

	java.util.List<CategoryEntity> findByProfileId(Long profileId);

	java.util.Optional<CategoryEntity> findByIdAndProfileId(Long id, Long profileId);
	
	java.util.List<CategoryEntity> findByTypeAndProfileId(String type, Long profileId);

	Boolean existsByNameAndProfileId(String name, Long profileId);

}