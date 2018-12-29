package com.lovage.sports.security;

import org.springframework.security.core.Authentication;

import com.lovage.sports.web.domain.LoginUser;

public interface SecurityService {

	public String findLoggedInUsername();

	public Authentication autologin(String username, String password);

	public boolean checkUserLogin(LoginUser loginUser);

	public void logout();

}
