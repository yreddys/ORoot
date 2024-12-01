package com.root.exception;

public class UserAlreadyRegistered extends Exception {
	String message;

	public UserAlreadyRegistered(String message) {
		super(message);
	}
}
