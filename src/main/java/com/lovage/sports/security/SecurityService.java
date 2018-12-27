package com.lovage.sports.security;

public interface SecurityService {

	public String findLoggedInUsername();

	public void autologin(String username, String password);

	public void logout();

}
