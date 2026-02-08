package com.web2.safia.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler({
			DomainException.class,
			EntityNotFoundException.class,
			UsernameNotFoundException.class,
			MethodArgumentNotValidException.class,
			IllegalArgumentException.class })
	public ProblemDetail handleBusinessExceptions(Exception ex) {
		logger.error("Business Exception: ", ex);

		return ProblemDetail
				.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
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
