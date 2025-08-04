package com.web2.sofia.sofia.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web2.sofia.sofia.exceptions.DomainException;
import com.web2.sofia.sofia.models.Endereco;
import com.web2.sofia.sofia.models.Fazenda;
import com.web2.sofia.sofia.services.EnderecoService;

@Controller
@RequestMapping("/public")
public class PublicController {
	private static final Logger logger = LoggerFactory.getLogger(PublicController.class);

	private final EnderecoService enderecoService;

	public PublicController(EnderecoService enderecoService) {
		this.enderecoService = enderecoService;
	}

	@Cacheable(value = "teste", key = "'minha-key-privada'")
	@GetMapping("test")
	public Fazenda teste() {
		var fazenda = new Fazenda("Óios D'Água");
		logger.info("Tem alguem tentando fazer um teste maroto: {}", fazenda);
		return fazenda;
	}

	@GetMapping("/cep/{cep}")
	public String teste2(@PathVariable(name = "cep") String cep) {
		try {
			Endereco endereco = enderecoService.findByCep(cep);
			logger.info("Endereco encontrado: {}", endereco);
		} catch (DomainException de) {
			logger.error("Erro ao buscar CEP: {}", de.getMessage());
		}

		return "/login/index.html";
	}
}
