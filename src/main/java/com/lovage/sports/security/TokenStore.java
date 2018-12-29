package com.lovage.sports.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class TokenStore {

	private Set<String> tokens = new HashSet<String>();

	public Set<String> getTokens() {
		return tokens;
	}
}
