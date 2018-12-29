package com.lovage.sports.security;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lovage.sports.domain.User;
import com.lovage.sports.service.UserService;
import com.lovage.sports.web.domain.LoginUser;

@Service
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

	@Override
	public String autologin(String username, String password) {

		String jwtToken = jwtTokenProvider.createToken(username, userService.findUserByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found")).getRoles());

		authenticate(username, password);

		tokenStore.getTokens().add(jwtToken);
		return jwtToken;
	}

	@Override
	public boolean checkUserLogin(LoginUser loginUser) {

		Optional<User> user = userService.findUserByEmail(loginUser.getEmail());
		if (user.isPresent() && user.get().isEnabled()) {
			return bCryptPasswordEncoder.matches(loginUser.getPassword(), user.get().getPassword());
		}
		return false;
	}

	@Override
	public void logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
		SecurityContextHolder.clearContext();
	}

	private void authenticate(String username, String password) {

		UserDetails userDetails = userService.loadUserByUsername(username);

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, password, userDetails.getAuthorities());

		authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		if (usernamePasswordAuthenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			logger.debug(String.format("Auto login %s successfully!", username));
		}
	}
}