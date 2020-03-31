package com.hackpro.authserver.exception;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
public class PersistentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PersistentException(String message) {
		super(message);
	}

}
