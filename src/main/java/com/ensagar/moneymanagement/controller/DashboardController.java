package com.ensagar.moneymanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ensagar.moneymanagement.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

	private final DashboardService dashboardService; 
	
	@GetMapping
	public ResponseEntity<java.util.Map<String, Object>> getDashboardData(){
		java.util.Map<String, Object> response = dashboardService.getDashboardData();
		return ResponseEntity.ok(response);
	}
}
