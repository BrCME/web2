package com.web2.sofia.sofia.models.builders;

import org.springframework.util.StringUtils;

import com.web2.sofia.sofia.models.Fazenda;

public class FazendaBuilder {
	private Fazenda instance;

	public FazendaBuilder builder() {
		instance = new Fazenda();
		return this;
	}

	public FazendaBuilder withNome(String nome) {
		if (!StringUtils.hasText(nome)) {
			throw new IllegalArgumentException("Nome inválido");
		}

		instance.setNome(nome);
		return this;
	}

	private void reset() {
		instance = null;
	}

	public Fazenda build() {
		Fazenda fazenda = instance;
		reset();
		return fazenda;
	}
}
