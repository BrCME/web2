package com.web2.sofia.sofia.repositories.adapters;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.web2.sofia.sofia.models.Endereco;
import com.web2.sofia.sofia.repositories.ConsumerEnderecoRepository;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepEnderecoRepository extends ConsumerEnderecoRepository {
	@Override
	@GetMapping("/{cep}/json")
	Optional<Endereco> findByCep(@PathVariable("cep") String cep);
}
