package com.example.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Clase para excepcion de Respose.
 * 
 * @author PABI1
 *
 */
@Data
@AllArgsConstructor
public class ExceptionResponse {

	private LocalDateTime date;
	private String message;
	private String description;

}