package com.lovage.sports.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lovage.sports.domain.User;
import com.lovage.sports.security.SecurityService;
import com.lovage.sports.service.UserService;
import com.lovage.sports.validation.EmailExistsException;
import com.lovage.sports.web.domain.SignupUser;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<SignupUser> registerUserAccount(@RequestBody @Valid SignupUser signupUser,
			BindingResult result) {

		User registered = new User();
		if (!result.hasErrors()) {
			registered = registerUser(signupUser, result);
		}

		if (registered == null) {
			result.rejectValue("email", "message.regError");
		}

		if (result.hasErrors()) {
			return new ResponseEntity<SignupUser>(signupUser, HttpStatus.BAD_REQUEST);
		} else {

			securityService.autologin(signupUser.getEmail(), signupUser.getMatchingPassword());
			return new ResponseEntity<SignupUser>(signupUser, HttpStatus.CREATED);
		}
	}

	// @CrossOrigin(origins = "http://localhost:4200")
	// @RequestMapping(value = "/login", method = RequestMethod.POST, consumes =
	// MediaType.APPLICATION_JSON_VALUE, produces =
	// MediaType.APPLICATION_JSON_VALUE)
	// @ResponseBody
	// public ResponseEntity<LoginUser> login(@RequestBody @Valid LoginUser
	// loginUser, BindingResult result) {
	// System.out.println("Checking Login Credentials...");
	//
	// boolean login = false;
	// if (!result.hasErrors()) {
	// login = loginUser(loginUser);
	// }
	//
	// if (!login) {
	// result.rejectValue("email", "message.regError");
	// }
	//
	// if (login) {
	// System.out.println("Found...");
	// return new ResponseEntity<LoginUser>(loginUser, HttpStatus.ACCEPTED);
	// }
	//
	// System.out.println("Not Found...");
	// return new ResponseEntity<LoginUser>(loginUser, HttpStatus.NOT_FOUND);
	// }

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/logout", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Boolean> logout() {
		System.out.println("Logging out ...");

		securityService.logout();

		return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
	}

	private User registerUser(SignupUser signupUser, BindingResult result) {
		User registered = null;
		try {
			registered = userService.registerNewUserAccount(signupUser);
		} catch (EmailExistsException e) {
			return null;
		}
		return registered;
	}

	// private boolean loginUser(LoginUser loginUser) {
	// boolean isValid = userService.checkUserLogin(loginUser);
	//
	// if (isValid) {
	// securityService.autologin(loginUser.getEmail(), loginUser.getPassword());
	// }
	// return isValid;
	// }
}
