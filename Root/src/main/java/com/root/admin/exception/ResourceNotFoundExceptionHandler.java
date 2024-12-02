package com.root.admin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.root.exception.ErrorMessage;

@ControllerAdvice
public class ResourceNotFoundExceptionHandler {
@ExceptionHandler(ResourceNotFoundException.class)
ResponseEntity<ErrorMessage> ResourceNotFoundExceptionMethod(ResourceNotFoundException re){
	ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT.value(), // HTTP status code (409 Conflict)
			re.getMessage() // Exception message
	);
	return new ResponseEntity<>(errorMessage,HttpStatus.CONFLICT);
}
}
