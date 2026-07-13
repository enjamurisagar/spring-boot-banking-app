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
import com.ensagar.moneymanagement.service.ExpenseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses")
public class ExpenseController {

	private final ExpenseService expenseService;
	
	@PostMapping
	public ResponseEntity<ExpenseDTO> addExpense(@RequestBody ExpenseDTO dto) {
		ExpenseDTO responseDTO = expenseService.addExpense(dto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
	}
	
	@GetMapping
	public ResponseEntity<java.util.List<ExpenseDTO>> getExpenses() {
		java.util.List<ExpenseDTO> responseDTO = expenseService.getCurrentMonthExpensesForCurrentUser();
		
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteExpenseById(@PathVariable Long id){
		expenseService.deleteExpenseById(id);
		return ResponseEntity.noContent().build();
	}
}
