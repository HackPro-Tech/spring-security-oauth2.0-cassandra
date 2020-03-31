package com.hackpro.authserver.exception;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
public class UserAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsException(String message) {
		super(message);
	}

}