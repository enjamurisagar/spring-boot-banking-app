package com.ensagar.moneymanagement.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ensagar.moneymanagement.entity.ExpenseEntity;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long>{

	//SELECT * FROM TBL_EXPENSE WHERE PROFILE_ID = ? ORDER BY DATE DESC;
	java.util.List<ExpenseEntity> findByProfileIdOrderByDateDesc(Long profileId);
	
	//SELECT * FROM TBL_EXPENSE WHERE PROFILE_ID = ? ORDER BY DATE DESC LIMIT 5;
	java.util.List<ExpenseEntity> findTop5ByProfileIdOrderByDateDesc(Long profileId);
	
	//JPQL Query
	@Query("SELECT SUM(E.amount) FROM ExpenseEntity E WHERE E.profile.id = :profileId")
	BigDecimal findTotalExpenseByProfileId(@Param("profileId") Long profileId);
	
	//SELECT * FROM TBL_EXPENSE WHERE profile_id = ? and date between startDate and endDate and name like '%?%' 
	java.util.List<ExpenseEntity> findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(Long profileId, LocalDate startDate, LocalDate endDate, String name, Sort sort);
	
	//SELECT * FROM TBL_EXPENSE WHERE PROFILE_ID = ? and DATE between ? and ?;
	java.util.List<ExpenseEntity> findByProfileIdAndDateBetween(Long profileId, LocalDate fromDate, LocalDate toDate);
	
}
