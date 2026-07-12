package com.ensagar.moneymanagement.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ensagar.moneymanagement.security.JwtRequestFilter;
import com.ensagar.moneymanagement.service.AppUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final AppUserDetailsService appUserDetailsService;
	private final JwtRequestFilter jwtRequestFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		return http.cors(Customizer.withDefaults())
		.csrf(AbstractHttpConfigurer::disable)
		.authorizeHttpRequests(auth -> auth.requestMatchers("/status", "/health", "/login", "/register", "/activate", "/test").permitAll()
				.anyRequest().authenticated())
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.addFilterBefore(jwtRequestFilter, BasicAuthenticationFilter.class)
		.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		
		configuration.setAllowedOriginPatterns(List.of("*"));
		configuration.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "accept"));
		configuration.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(appUserDetailsService);
//		authenticationProvider.setUserDetailsPasswordService(appUserDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return new ProviderManager(authenticationProvider);
		
	}
}
