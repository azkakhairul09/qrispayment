package com.sangbango.project.exceptions;

public class UserServiceException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3514516933962664480L;

	public UserServiceException(String errorDesc) {
		super(errorDesc);
	}
	
	public UserServiceException(String errorDesc, Throwable rootCause) {
		super(errorDesc, rootCause);
	}
}
