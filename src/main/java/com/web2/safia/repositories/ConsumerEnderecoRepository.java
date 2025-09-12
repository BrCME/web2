package com.web2.safia.repositories;

import java.util.Optional;

import com.web2.safia.models.Address;

public interface ConsumerEnderecoRepository {
	Optional<Address> findByCep(String cep);
}
