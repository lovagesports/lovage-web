package com.lovage.sports.security;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lovage.sports.web.domain.LoginUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final JwtConfig jwtConfig;

	private SecurityService securityService;

	public JwtUsernameAndPasswordAuthenticationFilter(JwtConfig jwtConfig, SecurityService securityService) {
		this.jwtConfig = jwtConfig;
		this.securityService = securityService;

		// By default, UsernamePasswordAuthenticationFilter listens to "/login" path.
		// In our case, we use "/auth". So, we need to override the defaults.
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(jwtConfig.getUri(), "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {

			// 1. Get credentials from request
			LoginUser creds = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);
			Authentication auth = null;

			boolean isValid = securityService.checkUserLogin(creds);
			if (isValid) {

				// 2. Create auth object (contains credentials) which will be used by auth
				// manager
				// 3. Authentication manager authenticate the user, and use
				// UserDetialsServiceImpl::loadUserByUsername() method to load the user.
				auth = securityService.autologin(creds.getEmail(), creds.getPassword());

			}
			return auth;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// Upon successful authentication, generate a token.
	// The 'auth' passed to successfulAuthentication() is the current authenticated
	// user.
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		Claims claims = Jwts.claims().setSubject(auth.getName());
		claims.put("roles",
				auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

		Date now = new Date();
		Date validity = new Date(now.getTime() + jwtConfig.getExpiration() * 1000);
		String token = Jwts.builder()//
				.setClaims(claims)//
				.setIssuedAt(now)//
				.setExpiration(validity)//
				.signWith(SignatureAlgorithm.HS256, jwtConfig.getSecret())//
				.compact();

		// Add token to header
		response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);
	}
}