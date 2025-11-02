package com.web2.safia.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.web2.safia.exceptions.DomainException;
import com.web2.safia.models.Address;
import com.web2.safia.repositories.ConsumerEnderecoRepository;
import com.web2.safia.repositories.adapters.JpaEnderecoRepository;
import com.web2.safia.repositories.adapters.ViaCepEnderecoRepository;

@Service
public class EnderecoService {
	private static final Logger logger = LoggerFactory.getLogger(EnderecoServiceTest.class);

	private final ConsumerEnderecoRepository apiEnderecoRepository;
	private final JpaEnderecoRepository jpaEnderecoRepository;

	public EnderecoService(
			ViaCepEnderecoRepository apiEnderecoRepository,
			JpaEnderecoRepository jpaEnderecoRepository) {

		this.apiEnderecoRepository = apiEnderecoRepository;
		this.jpaEnderecoRepository = jpaEnderecoRepository;
	}

	@Cacheable(value = "cep", key = "{ #cep }")
	public Address findByCep(String cep) throws DomainException {
		Optional<Address> endereco = jpaEnderecoRepository.findByCep(cep);

		if (!endereco.isPresent()) {
			endereco = apiEnderecoRepository.findByCep(cep);

			if (!endereco.isPresent()) {
				throw new DomainException("Não existe endereço para o CEP informado");
			}

			logger.info("Endereço buscado via API: {}", endereco.get());
			jpaEnderecoRepository.save(endereco.get());
		}

		return endereco.get();
	}
}
