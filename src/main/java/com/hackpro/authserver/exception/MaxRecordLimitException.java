package com.hackpro.authserver.exception;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
public class MaxRecordLimitException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MaxRecordLimitException(String message) {
		super(message);
	}

}
