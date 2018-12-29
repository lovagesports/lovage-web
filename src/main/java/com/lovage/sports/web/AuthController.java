package com.lovage.sports.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lovage.sports.domain.User;
import com.lovage.sports.security.JwtTokenProvider;
import com.lovage.sports.security.SecurityService;
import com.lovage.sports.service.UserService;
import com.lovage.sports.validation.EmailExistsException;
import com.lovage.sports.web.domain.LoginUser;
import com.lovage.sports.web.domain.SignupUser;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	AuthenticationManager authenticationManager;

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

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<Object, Object>> login(@RequestBody @Valid LoginUser user) {
		try {
			String username = user.getEmail();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, user.getPassword()));
			String token = jwtTokenProvider.createToken(username, userService.findUserByEmail(username)
					.orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found")).getRoles());
			Map<Object, Object> model = new HashMap<>();
			model.put("username", username);
			model.put("token", token);
			return new ResponseEntity<Map<Object, Object>>(model, HttpStatus.ACCEPTED);
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password supplied");
		}
	}

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
