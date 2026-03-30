package com.web2.safia.exceptions;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import jakarta.validation.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler({
			EntityNotFoundException.class,
			UsernameNotFoundException.class,
			MethodArgumentNotValidException.class,
			IllegalArgumentException.class })
	public ProblemDetail handleBusinessExceptions(Exception ex, WebRequest request) {
		logger.error("{}: {}", ex.getClass().getCanonicalName(), ex);
		logger.error("Request: ", request);

		var response = ProblemDetail
			.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
		
		return response;
	}

	@ExceptionHandler( {
		InputValidationException.class,
		ValidationException.class
	})
	public ProblemDetail handleValidationExceptions(Exception ex, WebRequest request) {
		logger.error("Validation Exception: ", ex);

		var response = ProblemDetail
			.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());

		return response;
	}

	@ExceptionHandler({
			DataAccessException.class
	})
	public ProblemDetail handleJpaExceptions(Exception ex) {
		logger.error("JPA Exception: ", ex);

		return ProblemDetail
				.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ Exception.class })
	public ProblemDetail handleGenericExceptions(Exception ex) {
		logger.error("Unhandled Generic Exception: ", ex);

		return ProblemDetail
			.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
