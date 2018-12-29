package com.lovage.sports.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Deprecated
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

	private final JwtConfig jwtConfig;

	public JwtTokenAuthenticationFilter(JwtConfig jwtConfig) {
		this.jwtConfig = jwtConfig;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		// 1. get the authentication header. Tokens are supposed to be passed in the
		// authentication header
		String header = request.getHeader(jwtConfig.getHeader());

		// 2. validate the header and check the prefix
		if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
			chain.doFilter(request, response); // If not valid, go to the next filter.
			return;
		}

		// If there is no token provided and hence the user won't be authenticated.
		// It's Ok. Maybe the user accessing a public path or asking for a token.

		// All secured paths that needs a token are already defined and secured in
		// config class.
		// And If user tried to access without access token, then he won't be
		// authenticated and an exception will be thrown.

		// 3. Get the token
		String token = header.replace(jwtConfig.getPrefix(), "");

		try { // exceptions might be thrown in creating the claims if for example the token is
				// expired

			// 4. Validate the token
			Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token)
					.getBody();

			String authenticatedUsername = null;
			List<String> authenticationAuthorities = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.isAuthenticated()) {
				User user = (User) authentication.getPrincipal();
				if (user != null) {
					authenticatedUsername = user.getUsername().toString();
					authenticationAuthorities = user.getAuthorities().stream().map((GrantedAuthority a) -> {
						return a.getAuthority();
					}).collect(Collectors.toList());
				}
			}

			String tokenUsername = claims.getSubject();
			@SuppressWarnings("unchecked")
			List<String> tokenAuthorities = (List<String>) claims.get("authorities");

			if (tokenUsername != null && authenticationAuthorities != null
					&& tokenUsername.equals(authenticatedUsername)
					&& authenticationAuthorities.containsAll(tokenAuthorities)) {
				System.out.println("test");
			}

		} catch (Exception e) {
			// In case of failure. Make sure it's clear; so guarantee user won't be
			// authenticated
			SecurityContextHolder.clearContext();
		}

		// go to the next filter in the filter chain
		chain.doFilter(request, response);
	}

}