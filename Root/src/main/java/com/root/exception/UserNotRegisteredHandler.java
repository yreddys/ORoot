package com.root.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserNotRegisteredHandler {
	@ExceptionHandler(UserNotRegistered.class)
	ResponseEntity<ErrorMessage> UserNotRegisteredMethod(UserNotRegistered e) {
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT.value(), // HTTP status code (409 Conflict)
				e.getMessage() // Exception message
		);
		return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
	}
}
