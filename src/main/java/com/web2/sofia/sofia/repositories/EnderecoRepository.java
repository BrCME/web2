package com.web2.sofia.sofia.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web2.sofia.sofia.models.Endereco;
import com.web2.sofia.sofia.repositories.EnderecoRepository;

public interface EnderecoRepository extends ConsumerEnderecoRepository, JpaRepository<Endereco, UUID> {
	@Override
	@Query("SELECT e FROM endereco e WHERE e.cep = :cep")
	Optional<Endereco> findByCep(@Param(value = "cep") String cep);
}
