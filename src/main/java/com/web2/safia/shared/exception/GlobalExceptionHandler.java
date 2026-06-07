package com.web2.safia.shared.exception;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

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

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler({
			DomainException.class
	})
	public ProblemDetail handleDomainExceptions(DomainException ex, WebRequest request) {
		logger.warn("Domain Exception: {}", ex);

		var response = ProblemDetail
				.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());

		setWebProperties(response, request);

		return response;
	}

	@ExceptionHandler({
			EntityNotFoundException.class,
			UsernameNotFoundException.class,
			IllegalArgumentException.class
	})
	public ProblemDetail handleBusinessExceptions(Exception ex, WebRequest request) {
		logger.warn("Business Exception: {}", ex);
		logger.warn("{}: {}", ex.getClass().getCanonicalName(), ex);
		logger.warn("Request: ", request);

		var response = ProblemDetail
				.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());

		setWebProperties(response, request);

		return response;
	}

	@ExceptionHandler({
			InputValidationException.class,
			ValidationException.class,
			ConstraintViolationException.class,
			MethodArgumentNotValidException.class
	})
	public ProblemDetail handleValidationExceptions(ConstraintViolationException ex, WebRequest request) {
		logger.warn("Validation Exception: ", ex);

		var response = ProblemDetail
				.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());

		Map<String, String> errors = new TreeMap<>();
		var violations = ex.getConstraintViolations();

		violations.forEach(error -> {
			logger.warn("Violation: {}: {}", error.getPropertyPath(), error.getMessage());

		});
		response.setProperty("fields", violations);

		setWebProperties(response, request);

		return response;
	}

	@ExceptionHandler({ DataAccessException.class })
	public ProblemDetail handleJpaExceptions(Exception ex, WebRequest request) {
		logger.warn("JPA Exception: ", ex);

		var response = ProblemDetail
				.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

		setWebProperties(response, request);

		return response;
	}

	@ExceptionHandler({ Exception.class })
	public ProblemDetail handleGenericExceptions(Exception ex, WebRequest request) {
		logger.warn("Unhandled Generic Exception: ", ex);

		var response = ProblemDetail
				.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

		setWebProperties(response, request);

		return response;
	}

	private void setWebProperties(ProblemDetail problemDetail, WebRequest request) {
		problemDetail.setProperty(WebExceptionAttributeType.SESSION.getNameInLowerCase(), request.getSessionId());
		problemDetail.setProperty(WebExceptionAttributeType.REMOTE.getNameInLowerCase(), request.getRemoteUser());
		problemDetail.setProperty(WebExceptionAttributeType.PRINCIPAL.getNameInLowerCase(), request.getUserPrincipal());
		problemDetail.setProperty(WebExceptionAttributeType.SECURE.getNameInLowerCase(), request.isSecure());
		problemDetail.setProperty(WebExceptionAttributeType.LOCALE.getNameInLowerCase(), request.getLocale());
		problemDetail.setProperty(WebExceptionAttributeType.CONTEXT.getNameInLowerCase(), request.getContextPath());

		var headers = request.getHeaderNames();
		while (headers.hasNext()) {
			problemDetail.setProperty(headers.toString(), request.getHeaderValues(headers.toString()));
			headers.next();
		}

		problemDetail.setProperty(WebExceptionAttributeType.TIMESTAMP.getNameInLowerCase(), LocalDateTime.now());
	}
}
