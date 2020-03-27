package com.hackpro.authserver.exception;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AuthenticationException(String message) {
		super(message);
	}

}
