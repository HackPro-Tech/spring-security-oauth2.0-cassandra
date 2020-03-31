package com.hackpro.authserver.exception;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ServiceException(String message) {
		super(message);
	}

}