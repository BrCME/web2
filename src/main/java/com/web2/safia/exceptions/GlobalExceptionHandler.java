package com.web2.safia.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler({
			DomainException.class,
			IllegalArgumentException.class})
	public String handleExceptions(Exception ex) {
		logger.error("Exceção encontrada: {}", ex.getMessage());

		return "error/404.html";
	}
}
