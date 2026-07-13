package com.ensagar.moneymanagement.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.ensagar.moneymanagement.assembler.IncomeAssembler;
import com.ensagar.moneymanagement.dto.ExpenseDTO;
import com.ensagar.moneymanagement.dto.IncomeDTO;
import com.ensagar.moneymanagement.entity.CategoryEntity;
import com.ensagar.moneymanagement.entity.ExpenseEntity;
import com.ensagar.moneymanagement.entity.IncomeEntity;
import com.ensagar.moneymanagement.entity.ProfileEntity;
import com.ensagar.moneymanagement.repository.CategoryRepository;
import com.ensagar.moneymanagement.repository.IncomeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncomeService {

	private final CategoryRepository categoryRepository;
	private final IncomeRepository incomeRepository;
	private final ProfileService profileService; 
	private final IncomeAssembler assembler;
	
	public IncomeDTO addIncome(IncomeDTO dto) {
		ProfileEntity profile = profileService.getCurrentProfile();
		CategoryEntity category =  categoryRepository.findById(dto.getCategoryId())
				.orElseThrow(() -> new RuntimeException("Category not found!"));
		IncomeEntity entity = assembler.toEntity(dto, profile, category);
		
		entity = incomeRepository.save(entity);
		return assembler.toDTO(entity);
	}
	
	public java.util.List<IncomeDTO> getCurrentMonthIncomesForCurrentUser() {
		ProfileEntity profile = profileService.getCurrentProfile();

		LocalDate now = LocalDate.now();
		LocalDate startDate = now.withDayOfMonth(1);
		LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());
		
		java.util.List<IncomeEntity> list = incomeRepository.findByProfileIdAndDateBetween(profile.getId(), startDate, endDate);
		
		return list.stream().map(expense -> assembler.toDTO(expense)).toList();
	}
	
	public void deleteIncomeById(Long id) {
		ProfileEntity profile = profileService.getCurrentProfile();

		IncomeEntity entity = incomeRepository.findById(id).orElseThrow(() -> new RuntimeException("Incomes not found"));
		
		if(!entity.getProfile().getId().equals(profile.getId())) 
			throw new RuntimeException("UnAuthorized to delete the income");
		incomeRepository.delete(entity);
	}
	
	public java.util.List<IncomeDTO> getLatest5IncomesForCurrentUser() {
		ProfileEntity profile = profileService.getCurrentProfile();

		java.util.List<IncomeEntity> list = incomeRepository.findTop5ByProfileIdOrderByDateDesc(profile.getId());
		
		return list.stream().map(expense -> assembler.toDTO(expense)).toList();
	}
	
	public BigDecimal getTotalIncomeForCurrentUser() {
		ProfileEntity profile = profileService.getCurrentProfile();

		BigDecimal totalExpenses = incomeRepository.findTotalExpenseByProfileId(profile.getId());
		
		return totalExpenses != null ? totalExpenses : BigDecimal.ZERO;
	}
}
