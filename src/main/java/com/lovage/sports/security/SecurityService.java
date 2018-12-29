package com.lovage.sports.security;

import com.lovage.sports.web.domain.LoginUser;

public interface SecurityService {

	public String autologin(String username, String password);

	public boolean checkUserLogin(LoginUser loginUser);

	public void logout(String jwtToken);

}
