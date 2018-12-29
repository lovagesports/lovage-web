package com.lovage.sports.security;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtAuthenticationException extends AuthenticationException {
	private static final long serialVersionUID = 7475800654301124514L;

	public InvalidJwtAuthenticationException(String e) {
		super(e);
	}
}