package com.web2.sofia.sofia.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.web2.sofia.sofia.errors.exceptions.DomainException;
import com.web2.sofia.sofia.models.Endereco;
import com.web2.sofia.sofia.repositories.ConsumerEnderecoRepository;
import com.web2.sofia.sofia.repositories.EnderecoRepository;
import com.web2.sofia.sofia.repositories.adapters.ViaCepEnderecoRepository;

@Service
public class EnderecoService {
	private static final Logger logger = LoggerFactory.getLogger(EnderecoService.class);
	
	private final ConsumerEnderecoRepository apiEnderecoRepository;
	private final EnderecoRepository jpaEnderecoRepository;

	public EnderecoService(
			ViaCepEnderecoRepository apiEnderecoRepository,
			EnderecoRepository jpaEnderecoRepository) {

		this.apiEnderecoRepository = apiEnderecoRepository;
		this.jpaEnderecoRepository = jpaEnderecoRepository;
	}

	@Cacheable(value = "cep", key = "{ #cep }")
	public Endereco findByCep(String cep) throws DomainException {
		Optional<Endereco> endereco = jpaEnderecoRepository.findByCep(cep);

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
