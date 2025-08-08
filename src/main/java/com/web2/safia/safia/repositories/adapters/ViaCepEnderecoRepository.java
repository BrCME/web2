package com.web2.safia.safia.repositories.adapters;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.web2.safia.safia.models.Address;
import com.web2.safia.safia.repositories.ConsumerEnderecoRepository;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepEnderecoRepository extends ConsumerEnderecoRepository {
	@Override
	@GetMapping("/{cep}/json")
	Optional<Address> findByCep(@PathVariable("cep") String cep);
}
