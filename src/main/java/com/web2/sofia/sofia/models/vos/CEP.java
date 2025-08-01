package com.web2.sofia.sofia.models.vos;

public class CEP {
	private static final String PATTERN = "\\d{5}-\\d{3}";

	private String codigo;

	public CEP(String codigo) {
		if (codigo.matches(PATTERN)) {
			this.codigo = codigo;
		}
		
		throw new IllegalArgumentException("Código de CEP inválido");
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		if (codigo.matches(PATTERN)) {
			this.codigo = codigo;
		}
		
		throw new IllegalArgumentException("Código de CEP inválido");
	}

	@Override
	public String toString() {
		return "{ 'codigo': '" + codigo + "' }";
	}
}
