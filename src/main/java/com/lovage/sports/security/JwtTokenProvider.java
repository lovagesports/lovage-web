package com.lovage.sports.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.lovage.sports.domain.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtConfig jwtConfig;

	@PostConstruct
	protected void init() {
		jwtConfig.setSecret(Base64.getEncoder().encodeToString(jwtConfig.getSecret().getBytes()));
	}

	public String createToken(String username, List<Role> roles) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("roles", roles);
		Date now = new Date();
		Date validity = new Date(now.getTime() + jwtConfig.getExpiration() * 1000);
		return Jwts.builder()//
				.setClaims(claims)//
				.setIssuedAt(now)//
				.setExpiration(validity)//
				.signWith(SignatureAlgorithm.HS256, jwtConfig.getSecret())//
				.compact();
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String getUsername(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	public String resolveToken(HttpServletRequest request) {

		// 1. get the authentication header. Tokens are supposed to be passed in the
		// authentication header
		String authHeader = request.getHeader(jwtConfig.getHeader());

		// 2. validate the header and check the prefix
		if (authHeader != null && authHeader.startsWith(jwtConfig.getPrefix())) {

			// 3. Get the token
			authHeader = authHeader.replace(jwtConfig.getPrefix(), "");
		}

		return authHeader;
	}

	public boolean validateToken(String token) {
		try {

			Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token).getBody();

			if (claims.getExpiration().before(new Date())) {
				return false;
			}

			String authenticatedUsername = null;
			List<String> authenticationAuthorities = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null || !authentication.isAuthenticated()) {
				return false;
			}

			User user = (User) authentication.getPrincipal();
			if (user == null || !user.isEnabled()) {
				return false;
			}

			authenticatedUsername = user.getUsername().toString();
			authenticationAuthorities = user.getAuthorities().stream().map((GrantedAuthority a) -> {
				return a.getAuthority();
			}).collect(Collectors.toList());

			String tokenUsername = claims.getSubject();
			@SuppressWarnings("unchecked")
			List<String> tokenAuthorities = (List<String>) claims.get("roles");

			if (tokenUsername == null || authenticationAuthorities == null
					|| !tokenUsername.equals(authenticatedUsername)
					|| !authenticationAuthorities.containsAll(tokenAuthorities)) {
				return false;
			}

			return true;

		} catch (JwtException | IllegalArgumentException e) {
			// In case of failure. Make sure it's clear; so guarantee user won't be
			// authenticated
			SecurityContextHolder.clearContext();
			throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
		}
	}
}