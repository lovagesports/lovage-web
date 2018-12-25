package com.lovage.sports.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.lovage.sports.web.domain.SignupUser;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

	@Override
	public void initialize(PasswordMatches constraintAnnotation) {
	}

	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		SignupUser user = (SignupUser) obj;
		return user.getPassword().equals(user.getMatchingPassword());
	}
}
