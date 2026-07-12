package com.ensagar.moneymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ensagar.moneymanagement.entity.ProfileEntity;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

	public java.util.Optional<ProfileEntity> findByEmail(String email);
	
	public java.util.Optional<ProfileEntity> findByActivationToken(String token);
}
	