
CREATE TABLE endereco(
	id UUID PRIMARY KEY,
	cep VARCHAR(9) NOT NULL,
	logradouro VARCHAR(255),
	complemento VARCHAR(255),
	bairro VARCHAR(255),
	localidade VARCHAR(255),
	uf VARCHAR(2) NOT NULL,
	estado VARCHAR(64) NOT NULL,
	regiao VARCHAR(32) NOT NULL,
	ddd VARCHAR(2) NOT NULL
);
