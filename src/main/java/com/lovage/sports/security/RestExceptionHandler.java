package com.lovage.sports.security;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.ResponseEntity.status;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(value = { InvalidJwtAuthenticationException.class })
	public ResponseEntity<?> invalidJwtAuthentication(InvalidJwtAuthenticationException ex, WebRequest request) {
		return status(UNAUTHORIZED).build();
	}
}