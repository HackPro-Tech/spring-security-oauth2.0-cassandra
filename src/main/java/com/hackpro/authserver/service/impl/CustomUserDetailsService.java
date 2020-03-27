package com.hackpro.authserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hackpro.authserver.domain.UserDetailsPrincipal;
import com.hackpro.authserver.service.UserService;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	// Fetch User based on Email Address or Phone No
	@Override
	public UserDetailsPrincipal loadUserByUsername(String emailOrPhoneNo) throws UsernameNotFoundException {
		UserDetailsPrincipal user = userService.getUserByEmailOrPhoneNo(emailOrPhoneNo);
		if (user == null) {
			throw new UsernameNotFoundException("User not available, please check Email or PhoneNo");
		}
		return user;
	}
}
