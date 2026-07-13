package com.ensagar.moneymanagement.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.ensagar.moneymanagement.assembler.ExpenseAssembler;
import com.ensagar.moneymanagement.dto.ExpenseDTO;
import com.ensagar.moneymanagement.entity.CategoryEntity;
import com.ensagar.moneymanagement.entity.ExpenseEntity;
import com.ensagar.moneymanagement.entity.ProfileEntity;
import com.ensagar.moneymanagement.repository.CategoryRepository;
import com.ensagar.moneymanagement.repository.ExpenseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {

	private final CategoryService categoryService;
	private final CategoryRepository categoryRepository;
	private final ExpenseRepository expenseRepository;
	private final ProfileService profileService; 
	private final ExpenseAssembler assembler;
	
	
	public ExpenseDTO addExpense(ExpenseDTO dto) {
		ProfileEntity profile = profileService.getCurrentProfile();
		CategoryEntity category =  categoryRepository.findById(dto.getCategoryId())
				.orElseThrow(() -> new RuntimeException("Category not found!"));
		ExpenseEntity entity = assembler.toEntity(dto, profile, category);
		
		entity = expenseRepository.save(entity);
		return assembler.toDTO(entity);
	}
	
	public java.util.List<ExpenseDTO> getCurrentMonthExpensesForCurrentUser() {
		ProfileEntity profile = profileService.getCurrentProfile();

		LocalDate now = LocalDate.now();
		LocalDate startDate = now.withDayOfMonth(1);
		LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());
		
		java.util.List<ExpenseEntity> list = expenseRepository.findByProfileIdAndDateBetween(profile.getId(), startDate, endDate);
		
		return list.stream().map(expense -> assembler.toDTO(expense)).toList();
	}
	
	public void deleteExpenseById(Long id) {
		ProfileEntity profile = profileService.getCurrentProfile();

		ExpenseEntity entity = expenseRepository.findById(id).orElseThrow(() -> new RuntimeException("Expends not found"));
		
		if(!entity.getProfile().getId().equals(profile.getId())) 
			throw new RuntimeException("UnAuthorized to delete the expense");
		expenseRepository.delete(entity);
	}
	
	public java.util.List<ExpenseDTO> getLatest5ExpensesForCurrentUser() {
		ProfileEntity profile = profileService.getCurrentProfile();

		java.util.List<ExpenseEntity> list = expenseRepository.findTop5ByProfileIdOrderByDateDesc(profile.getId());
		
		return list.stream().map(expense -> assembler.toDTO(expense)).toList();
	}
	
	public BigDecimal getTotalExpenseForCurrentUser() {
		ProfileEntity profile = profileService.getCurrentProfile();

		BigDecimal totalExpenses = expenseRepository.findTotalExpenseByProfileId(profile.getId());
		
		return totalExpenses != null ? totalExpenses : BigDecimal.ZERO;
	}
}
