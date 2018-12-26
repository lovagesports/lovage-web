package com.lovage.sports.web.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.lovage.sports.validation.PasswordMatches;
import com.lovage.sports.validation.ValidEmail;

@PasswordMatches
public class SignupUser {

	@NotNull
	@NotEmpty
	private String firstName;

	@NotNull
	@NotEmpty
	private String lastName;

	@NotNull
	@NotEmpty
	private String password;
	private String matchingPassword;

	@ValidEmail
	@NotNull
	@NotEmpty
	private String email;

	@Override
	public String toString() {
		return "SignupUser [firstName=" + firstName + ", lastName=" + lastName + ", password=" + password
				+ ", matchingPassword=" + matchingPassword + ", email=" + email + "]";
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
