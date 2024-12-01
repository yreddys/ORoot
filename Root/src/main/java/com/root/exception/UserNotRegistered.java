package com.root.exception;

public class UserNotRegistered extends Exception {
	String message;

	public UserNotRegistered(String message) {
		super(message);
	}
}
