package com.bacancy.registrationapi.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionResponse {

	private String error_message;
	private String details;
	
}
