package com.ensagar.moneymanagement.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ensagar.moneymanagement.entity.IncomeEntity;

@Repository
public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {

	// SELECT * FROM TBL_INCOME WHERE PROFILE_ID = ? ORDER BY DATE DESC;
	java.util.List<IncomeEntity> findByProfileIdOrderByDateDesc(Long profileId);

	// SELECT * FROM TBL_INCOME WHERE PROFILE_ID = ? ORDER BY DATE DESC LIMIT 5;
	java.util.List<IncomeEntity> findTop5ByProfileIdOrderByDateDesc(Long profileId);

	// JPQL Query
	@Query("SELECT SUM(I.amount) FROM IncomeEntity I WHERE I.profile.id = :profileId")
	BigDecimal findTotalExpenseByProfileId(@Param("profileId") Long profileId);

	// SELECT * FROM TBL_INCOME WHERE profile_id = ? and date between startDate and
	// endDate and name like '%?%'
	java.util.List<IncomeEntity> findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(Long profileId,
			LocalDate startDate, LocalDate endDate, String name, Sort sort);

	// SELECT * FROM TBL_INCOME WHERE PROFILE_ID = ? and DATE between ? and ?;
	java.util.List<IncomeEntity> findByProfileIdAndDateBetween(Long profileId, LocalDate fromDate, LocalDate toDate);

}
