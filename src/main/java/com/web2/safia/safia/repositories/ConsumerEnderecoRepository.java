package com.web2.safia.safia.repositories;

import java.util.Optional;

import com.web2.safia.safia.models.Address;

public interface ConsumerEnderecoRepository {
	Optional<Address> findByCep(String cep);
}
