package com.web2.safia.safia.repositories.adapters;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web2.safia.safia.models.Address;
import com.web2.safia.safia.repositories.ConsumerEnderecoRepository;
import com.web2.safia.safia.repositories.adapters.JpaEnderecoRepository;

public interface JpaEnderecoRepository extends ConsumerEnderecoRepository, JpaRepository<Address, UUID> {
	@Override
	@Query("SELECT e FROM endereco e WHERE e.cep = :cep")
	Optional<Address> findByCep(@Param(value = "cep") String cep);
}
