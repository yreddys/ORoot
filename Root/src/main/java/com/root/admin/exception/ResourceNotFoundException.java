package com.root.admin.exception;

public class ResourceNotFoundException extends Exception {
	String message;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
