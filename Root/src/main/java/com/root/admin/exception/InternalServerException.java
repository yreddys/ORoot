package com.root.admin.exception;

public class InternalServerException extends Exception {
	String message;

	public InternalServerException(String message) {
		super(message);

	}
}
