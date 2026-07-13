package com.ensagar.moneymanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ensagar.moneymanagement.dto.CategoryDTO;
import com.ensagar.moneymanagement.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

	private final CategoryService categoryService; 
	
	@PostMapping
	public ResponseEntity<CategoryDTO> saveCategory(@RequestBody CategoryDTO dto){
		CategoryDTO savedCategory = categoryService.saveCategory(dto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
	}
	
	@GetMapping
	public ResponseEntity<java.util.List<CategoryDTO>> getCategories(){
		java.util.List<CategoryDTO> categories = categoryService.getCategoriesForCurrentUser();
		
		return ResponseEntity.ok(categories);
	}
	
	@GetMapping("/{type}")
	public ResponseEntity<java.util.List<CategoryDTO>> getCategoriesByType(@PathVariable String type) {
		java.util.List<CategoryDTO> categories = categoryService.getCategoriesByTypeForCurrentUser(type);
		
		return ResponseEntity.ok(categories);
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> getCategoriesByType(@PathVariable Long categoryId, @RequestBody CategoryDTO dto) {
		CategoryDTO category = categoryService.updateCategory(categoryId, dto);
		
		return ResponseEntity.ok(category);
	}
}
