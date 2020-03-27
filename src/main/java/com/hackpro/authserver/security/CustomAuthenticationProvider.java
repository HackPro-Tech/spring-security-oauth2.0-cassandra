package com.hackpro.authserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.hackpro.authserver.domain.UserDetailsPrincipal;
import com.hackpro.authserver.exception.AuthenticationException;
import com.hackpro.authserver.service.UserService;
import com.hackpro.authserver.utils.SecurityHelper;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		// Check whether the UserName(Email or PhoneNo) and Password are Valid
		checkUserNameAndPasswordIsValid(authentication);

		String emailOrPhoneNo = authentication.getName();
		String password = authentication.getCredentials().toString();

		// Fetch User Details Based on Email Or PhoneNo
		UserDetailsPrincipal user = userService.getUserByEmailOrPhoneNo(emailOrPhoneNo);

		// Check whether the User is Exist or Not
		checkUserNameAndPasswordIsExist(user);

		// Check Whether the Password is Matched or Not
		return checkPasswordIsMatched(user, emailOrPhoneNo, password);

	}

	private void checkUserNameAndPasswordIsValid(Authentication authentication) {
		if (authentication.getName() == null || "".equals(authentication.getName())) {
			throw new AuthenticationException("Username should not blank");
		} else if (authentication.getCredentials() == null || "".equals(authentication.getCredentials().toString())) {
			throw new AuthenticationException("Password should not blank");
		}
	}

	private void checkUserNameAndPasswordIsExist(UserDetailsPrincipal user) {
		if (user == null) {
			throw new AuthenticationException("Invalid Email/PhoneNo!!!");
		} else if (!user.isEnabled()) {
			throw new AuthenticationException("Account is blocked, Please contact admin");
		}
	}

	private UsernamePasswordAuthenticationToken checkPasswordIsMatched(UserDetailsPrincipal user, String emailOrPhone,
			String password) {
		if (SecurityHelper.isPasswordMatched(password, user.getPassword())) {
			return new UsernamePasswordAuthenticationToken(emailOrPhone, password, user.getAuthorities());
		} else {
			throw new AuthenticationException("Invalid Password!!!");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
