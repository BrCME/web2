package com.web2.safia.validations.strategies;

import org.springframework.util.StringUtils;

import com.web2.safia.validations.Validator;

public class CepValidator implements Validator<String> {
	private static final String PATTERN = "\\d{5}-\\d{3}";

	@Override
	public boolean validate(String t) {
		if (!StringUtils.hasText(t)) {
			throw new IllegalArgumentException("CEP não pode ser nulo");
		}

		return t.strip().matches(PATTERN);
	}
}
