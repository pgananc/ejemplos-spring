package com.example.exception;

public class ModelNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5208234351317362814L;

	public ModelNotFoundException(String mensaje) {
		super(mensaje);
	}
}
