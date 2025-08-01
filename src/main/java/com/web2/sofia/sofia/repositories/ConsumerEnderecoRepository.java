package com.web2.sofia.sofia.repositories;

import java.util.Optional;

import com.web2.sofia.sofia.models.Endereco;

public interface ConsumerEnderecoRepository {
	Optional<Endereco> findByCep(String cep);
}
