package com.example.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> manejarTodasExcepciones(ModelNotFoundException ex,
			WebRequest request) {
		ExceptionResponse er = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<ExceptionResponse>(er, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ModelNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> manejarModeloException(ModelNotFoundException ex,
			WebRequest request) {
		ExceptionResponse er = new ExceptionResponse(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<ExceptionResponse>(er, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder errors = listarErrores(ex);
		ExceptionResponse er = new ExceptionResponse(LocalDateTime.now(), errors.toString(),
				request.getDescription(false));
		return new ResponseEntity<Object>(er, HttpStatus.BAD_REQUEST);
	}

	private StringBuilder listarErrores(MethodArgumentNotValidException ex) {
		final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		final List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();

		StringBuilder errors = new StringBuilder();

		fieldErrors.forEach(fieldError -> errors.append(fieldError.getField()).append(": ")
				.append(fieldError.getDefaultMessage()).append(" "));

		globalErrors.forEach(globalError -> errors.append(globalError.getObjectName()).append(": ")
				.append(globalError.getDefaultMessage()).append(" "));
		return errors;
	}

}
