package com.ensagar.moneymanagement.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ensagar.moneymanagement.assembler.ProfileAssembler;
import com.ensagar.moneymanagement.dto.AuthDTO;
import com.ensagar.moneymanagement.dto.ProfileDTO;
import com.ensagar.moneymanagement.entity.ProfileEntity;
import com.ensagar.moneymanagement.repository.ProfileRepository;
import com.ensagar.moneymanagement.util.JWTUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {

	private final ProfileRepository profileRepository;
	private final ProfileAssembler profileAssembler;
	private final EmailService emailService;
	private final AuthenticationManager authenticationManager;
	private final JWTUtil jwtUtil;
	
	public ProfileDTO registerProfile(ProfileDTO request) {
		ProfileEntity entity = profileAssembler.toProfileEntity(request);
		entity.setActivationToken(UUID.randomUUID().toString());
		entity = profileRepository.save(entity);
		ProfileDTO responseDTO = profileAssembler.toProfileDTO(entity);
		
		//Send activation email
		String activationLink = "http://localhost:8080/api/v1/activate?token=" + entity.getActivationToken();
		String subject = "Activate your Money Manager Account";
		String emailBody = "Click on the following link to activate the account :" + activationLink;
		
		emailService.sendEmail(entity.getEmail(), subject, emailBody);
		
		return responseDTO;
	}
	
	public boolean activateProfile(String activationToken) {
		return profileRepository.findByActivationToken(activationToken).map(profile -> {
			profile.setIsActive(true);
			profileRepository.save(profile);
			return true;
		}).orElse(false);
	}
	
	
	public boolean isProfileActivated(String email) {
		return profileRepository.findByEmail(email)
				.map(ProfileEntity::getIsActive)
				.orElse(false);
	}
	
	public ProfileEntity getCurrentProfile() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String email = authentication.getName();
		
		return profileRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Profile not found with email :: " + email));
	}
	
	public ProfileDTO getPublicProfileDTO(String email) {
		ProfileEntity profile = null;
		if(email == null) {
			profile = getCurrentProfile();
		}
		profile = profileRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Profile not found with email :: " + email));
		return profileAssembler.toProfileDTO(profile);
	}

	public Map<String, Object> authenticateAndGenerateToken(AuthDTO authDTO) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword()));
			
			// Generate Token
			String token = jwtUtil.generateToken(authDTO.getEmail());
			return Map.of(
					"token", token,
					"user", getPublicProfileDTO(authDTO.getEmail())	
					);
		} catch (Exception e) {
			throw new RuntimeException("Invalid email or password");
		}
	}
}
