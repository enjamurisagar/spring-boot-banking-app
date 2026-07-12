package com.ensagar.moneymanagement.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTUtil {
	
	public static final String SECRET_KEY = "kchjgciuaewcacbjkiciuewegiuediaskjbdmszbdiuadgdqwdajbdj";
	
	public static final String JWT_HEADER = "Authorization";

	SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

	public String generateToken(String email) {
		
		String jwt = Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + 8460000000L))
				.claim("email", email)
				.signWith(key)
				.compact();
		
		return jwt;
	}
	
	public String getEmailFromToken(String jwt) {
		String email = null;
		if(jwt != null) {
			jwt = jwt.substring(7);
			
			Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
			
			email = String.valueOf(claims.get("email"));
		}
		return email;
	}
}
