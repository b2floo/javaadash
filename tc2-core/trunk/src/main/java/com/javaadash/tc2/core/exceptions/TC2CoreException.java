package com.javaadash.tc2.core.exceptions;

public class TC2CoreException extends Exception {
	private static final long serialVersionUID = -8036684065699723562L;
	
	private int errorCode;

	public TC2CoreException() {
		super();
	}
	
	public TC2CoreException(String message) {
		super(message);
	}
	
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
