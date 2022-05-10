package com.pichincha.test.excepions;

public class NotStoreException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotStoreException(String message) {
		super(message);
	}

}
