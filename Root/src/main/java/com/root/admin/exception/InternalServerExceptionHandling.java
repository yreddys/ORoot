package com.root.admin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.root.exception.ErrorMessage;

@ControllerAdvice
public class InternalServerExceptionHandling {
	@ExceptionHandler(InternalServerException.class)
	ResponseEntity<ErrorMessage> InternalServerExceptionMethod(InternalServerException in) {
		ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT.value(), // HTTP status code (409 Conflict)
				in.getMessage() // Exception message
		);
		return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
	}
}
