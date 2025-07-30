package com.web2.sofia.sofia.models;

import java.io.Serializable;

public class Fazenda implements Serializable {
	private String nome;

	public Fazenda() {
	}

	public Fazenda(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Fazenda [nome=" + nome + "]";
	}
}
