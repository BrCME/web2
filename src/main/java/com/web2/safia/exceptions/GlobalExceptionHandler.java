package com.web2.safia.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.thymeleaf.exceptions.TemplateEngineException;
import org.thymeleaf.exceptions.TemplateInputException;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler({
			DomainException.class,
			IllegalArgumentException.class })
	public String handleExceptions(Exception ex, Model model) {
		logger.error("Exceção de Domínio: ", ex);

		model.addAttribute("message", ex.getLocalizedMessage());
		model.addAttribute("code", HttpStatus.BAD_REQUEST.value());

		return "error.html";
	}

	@ExceptionHandler({
		MethodArgumentNotValidException.class})
	public String handleInvalidArgumentExceptions(MethodArgumentNotValidException  ex, Model model) {
		logger.error("Exceção de Validação: ", ex);
		
		model.addAttribute("message", ex.getAllErrors().stream().map(error -> error.getDefaultMessage()).toList());
		model.addAttribute("code", HttpStatus.BAD_REQUEST.value());

		return "error.html";
	}

	@ExceptionHandler({
			DataAccessException.class
	})
	public String handleJpaExceptions(Exception ex, Model model) {
		logger.error("Exceção de JPA: ", ex);

		model.addAttribute("message", ex.getLocalizedMessage());
		model.addAttribute("code", HttpStatus.SERVICE_UNAVAILABLE.value());

		return "error.html";
	}

	@ExceptionHandler({
		TemplateEngineException.class,
		TemplateInputException.class
	})
	public String handleTemplateExceptions(Exception ex, Model model) {
		logger.error("Exceção de Thymeleaf: ", ex);

		model.addAttribute("message", ex.getLocalizedMessage());
		model.addAttribute("code", HttpStatus.UNPROCESSABLE_ENTITY.value());
		
		return "error.html";
	}

	@ExceptionHandler({ Exception.class })
	public String handleGenericExceptions(Exception ex, Model model) {
		logger.error("Exceção extra: ", ex);

		model.addAttribute("message", ex.getLocalizedMessage());
		model.addAttribute("code", HttpStatus.INTERNAL_SERVER_ERROR.value());

		return "error.html";
	}
}
