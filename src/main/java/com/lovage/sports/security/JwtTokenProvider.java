package com.lovage.sports.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

	@Autowired
	private TokenStore tokenStore;

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

	private String getUsername(String token) {
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

			boolean inTokenStorage = tokenStore.getTokens().contains(token);

			return inTokenStorage;

		} catch (JwtException | IllegalArgumentException e) {
			// In case of failure. Make sure it's clear; so guarantee user won't be
			// authenticated
			SecurityContextHolder.clearContext();
			throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
		}
	}
}