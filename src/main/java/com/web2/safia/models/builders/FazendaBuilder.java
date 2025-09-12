package com.web2.safia.models.builders;

import org.springframework.util.StringUtils;

import com.web2.safia.models.Farm;

public class FazendaBuilder extends Builder<Farm> {
	public FazendaBuilder withNome(String nome) {
		if (!StringUtils.hasText(nome)) {
			throw new IllegalArgumentException("Nome inválido");
		}

		instance.setNome(nome);
		return this;
	}

	@Override
	public FazendaBuilder builder() {
		instance = new Farm();
		return this;
	}

	@Override
	public Farm build() {
		Farm fazenda = instance;
		reset();
		return fazenda;
	}
}
