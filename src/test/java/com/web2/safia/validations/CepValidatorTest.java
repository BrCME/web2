package com.web2.safia.validations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.web2.safia.validations.strategies.CepValidator;

class CepValidatorTest {
	private Validator<String> underTest;

	@BeforeEach
	void setUp() {
		underTest = new CepValidator();
	}

	@Test
	void givenValidCep_whenValidatingString_thenReturnTrue() {
		String validCep = "12345-123";

		assertTrue(underTest.validate(validCep));
	}

	@Test
	void givenInvalidCep_whenValidatingString_thenReturnTrue() {
		String invalidCep = "123-12345";

		assertFalse(underTest.validate(invalidCep));
	}

	@Test
	void givenNullCep_whenValidatingString_thenThrowException() {
		String nullCep = null;

		assertThrowsExactly(IllegalArgumentException.class, () -> underTest.validate(nullCep));
	}
}
