package com.web2.safia.validations.strategies;

import java.util.Optional;

import com.web2.safia.validations.Validator;

public class CepValidator implements Validator<String> {
	private static final String CEP_PATTERN = "\\d{3}.\\d{3}.\\d{3}-\\d{2}";

	@Override
	public boolean validate(Optional<String> t) {
		return t
				.filter(cep -> cep.strip().matches(CEP_PATTERN))
				.isPresent();
	}
}
