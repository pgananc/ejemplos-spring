package com.prueba.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class EmployeeNotFoundException extends HttpStatusCodeException {

	private static final long serialVersionUID = 73263616501570402L;

	public EmployeeNotFoundException() {
		super(HttpStatus.NOT_FOUND);
	}

	public EmployeeNotFoundException(String message) {
		super(HttpStatus.NOT_FOUND, message);
	}

}
