package com.ensagar.moneymanagement.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ensagar.moneymanagement.dto.ExpenseDTO;
import com.ensagar.moneymanagement.dto.IncomeDTO;
import com.ensagar.moneymanagement.dto.RecentTransactionDTO;
import com.ensagar.moneymanagement.entity.ProfileEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

	private final IncomeService incomeService;
	private final ExpenseService expenseService;
	private final ProfileService profileService;

	public java.util.Map<String, Object> getDashboardData() {
		ProfileEntity profile = profileService.getCurrentProfile();

		java.util.Map<String, Object> response = new java.util.LinkedHashMap<String, Object>();

		java.util.List<IncomeDTO> latestIncomes = incomeService.getLatest5IncomesForCurrentUser();
		java.util.List<ExpenseDTO> latestExpenses = expenseService.getLatest5ExpensesForCurrentUser();


		java.util.List<RecentTransactionDTO> recentTransactions = java.util.stream.Stream
				.concat(latestExpenses.stream()
						.map(expense -> RecentTransactionDTO.builder().id(expense.getId()).profileId(profile.getId())
								.icon(expense.getIcon()).name(expense.getName()).amount(expense.getAmount())
								.createdAt(expense.getCreatedAt()).updatedAt(expense.getUpdatedAt()).type("Expense")
								.build()),

						latestIncomes.stream()
								.map(income -> RecentTransactionDTO.builder().id(income.getId())
										.profileId(income.getId()).icon(income.getIcon()).name(income.getName())
										.amount(income.getAmount()).createdAt(income.getCreatedAt())
										.updatedAt(income.getUpdatedAt()).type("Income").build())
								.sorted((a, b) -> {
									int cmp = b.getDate().compareTo(a.getDate());
									if (cmp == 0 && a.getCreatedAt() != null && b.getCreatedAt() != null) {
										return b.getCreatedAt().compareTo(a.getCreatedAt());
									}
									return cmp;
								})).collect(Collectors.toList());
		response.put("totalBalance", incomeService.getTotalIncomeForCurrentUser()
				.subtract(expenseService.getTotalExpenseForCurrentUser()));
		response.put("totalIncome", incomeService.getTotalIncomeForCurrentUser());
		response.put("totalExpense", expenseService.getTotalExpenseForCurrentUser());
		response.put("recent5Expenses", latestExpenses);
		response.put("recent5Incomes", latestIncomes);
		response.put("recentTransactions", recentTransactions);
		return response;
	}
}
