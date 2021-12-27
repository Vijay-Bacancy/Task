package com.bacancy.registrationapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bacancy.registrationapi.util.ExceptionResponse;

@ControllerAdvice
@RestController
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(CustomerAlreadyExistsException.class)
	public final ResponseEntity<Object> handleCustomerAlreadyExistsException
		(CustomerAlreadyExistsException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = 
				new ExceptionResponse(ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(CustomerNotFoundException.class)
	public final ResponseEntity<Object> handleCustomerNotFoundException
		(CustomerNotFoundException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = 
				new ExceptionResponse(ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
}
