package com.lovage.sports.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lovage.sports.domain.Role;
import com.lovage.sports.domain.User;
import com.lovage.sports.repo.RoleRepository;
import com.lovage.sports.repo.UserRepository;
import com.lovage.sports.validation.EmailExistsException;
import com.lovage.sports.web.domain.LoginUser;
import com.lovage.sports.web.domain.SignupUser;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Transactional
	public User registerNewUserAccount(SignupUser signupUser) throws EmailExistsException {

		if (emailExist(signupUser.getEmail())) {
			throw new EmailExistsException("There is an account with that email address:" + signupUser.getEmail());
		}
		User user = new User();
		user.setFullName(signupUser.getFullName());
		user.setPassword(bCryptPasswordEncoder.encode(signupUser.getPassword()));
		user.setEmail(signupUser.getEmail());

		Role userRole = roleRepository.findByRole("ROLE_USER");
		user.setRoles(Arrays.asList(userRole));

		User saved = userRepository.save(user);
		return saved;
	}

	@Transactional
	public boolean checkUserLogin(LoginUser loginUser) {
		User user = userRepository.findByEmail(loginUser.getEmail());
		if (user != null) {
			return bCryptPasswordEncoder.matches(loginUser.getPassword(), user.getPassword());
		}
		return false;
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(email);
		if (user != null) {
			List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
			return buildUserForAuthentication(user, authorities);
		} else {
			throw new UsernameNotFoundException("username not found");
		}
	}

	private boolean emailExist(String email) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			return true;
		}
		return false;
	}

	private List<GrantedAuthority> getUserAuthority(List<Role> userRoles) {
		Set<GrantedAuthority> roles = new HashSet<>();
		userRoles.forEach((role) -> {
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		});

		List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
		return grantedAuthorities;
	}

	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}
}
