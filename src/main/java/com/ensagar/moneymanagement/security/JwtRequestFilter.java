package com.ensagar.moneymanagement.security;

import java.io.IOException;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ensagar.moneymanagement.util.JWTUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter{

	private final UserDetailsService userDetailsService;
	private final JWTUtil jwtUtil;
	
	public static final String SECRET_KEY = "kchjgciuaewcacbjkiciuewegiuediaskjbdmszbdiuadgdqwdajbdj";
	
	public static final String JWT_HEADER = "Authorization";
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String jwt = request.getHeader(JWT_HEADER);
		
		if(jwt != null) {
			//Bearer *****
			System.out.println("jwt :: " + jwt);
			jwt = jwt.substring(7);
			
			try {
				SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
				
				Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
				
				String email = String.valueOf(claims.get("email"));
				System.out.println("email after decryption :: " + email);
				String authorities = String.valueOf(claims.get("autorities"));
				
				java.util.List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
				
				Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auths);
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				
			} catch (Exception e) {
				System.out.println("Exception while authenticating user");
				throw new BadCredentialsException("Invalid Token from JWTValidator");
			}
		}
		
		filterChain.doFilter(request, response);
		
	}
	
}
