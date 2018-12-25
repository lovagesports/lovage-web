package com.lovage.sports.web.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.lovage.sports.validation.ValidEmail;

public class LoginUser {

	@NotNull
	@NotEmpty
	private String password;

	@ValidEmail
	@NotNull
	@NotEmpty
	private String email;

	@Override
	public String toString() {
		return "LoginUser [password=" + password + ", email=" + email + "]";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
