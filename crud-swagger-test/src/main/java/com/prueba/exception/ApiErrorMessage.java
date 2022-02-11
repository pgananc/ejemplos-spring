package com.prueba.exception;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ApiErrorMessage {
	private String errorMessage;
	private String requestingURI;
	private LocalDateTime timeStamp;

	public ApiErrorMessage(String messageError, String URI, LocalDateTime timeStamp) {
		this.errorMessage = messageError;
		this.requestingURI = URI;
		this.timeStamp = timeStamp;
	}
}
