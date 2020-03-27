package com.hackpro.authserver.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityHelper {

	private static final int ROUNDS = 12;

	public static boolean isPasswordMatched(final String plainPassword, final String bCryptHash) {
		return new BCryptPasswordEncoder(ROUNDS).matches(plainPassword, bCryptHash);
	}

}
