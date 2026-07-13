package com.ensagar.moneymanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ensagar.moneymanagement.dto.ExpenseDTO;
import com.ensagar.moneymanagement.dto.IncomeDTO;
import com.ensagar.moneymanagement.service.IncomeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/incomes")
public class IncomeController {

	private final IncomeService incomeService;

	@PostMapping
	public ResponseEntity<IncomeDTO> addExpense(@RequestBody IncomeDTO dto) {
		IncomeDTO responseDTO = incomeService.addIncome(dto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
	}
	
	@GetMapping
	public ResponseEntity<java.util.List<IncomeDTO>> getExpenses() {
		java.util.List<IncomeDTO> responseDTO = incomeService.getCurrentMonthIncomesForCurrentUser();
		
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteIncomeById(@PathVariable Long id){
		incomeService.deleteIncomeById(id);
		return ResponseEntity.noContent().build();
	}
}
