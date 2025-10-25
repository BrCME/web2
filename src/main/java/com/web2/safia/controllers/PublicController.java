package com.web2.safia.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web2.safia.exceptions.DomainException;
import com.web2.safia.models.Address;
import com.web2.safia.services.EnderecoService;

@Controller
@RequestMapping("/")
public class PublicController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	private final EnderecoService enderecoService;

	public PublicController(EnderecoService enderecoService) {
		this.enderecoService = enderecoService;
	}

	@GetMapping("")
	public String getLandingPage() {
		return "index.html";
	}

	@GetMapping("/cep/{cep}")
	public String teste2(@PathVariable(name = "cep") String cep) {
		try {
			Address endereco = enderecoService.findByCep(cep);
			logger.info("Endereco encontrado: {}", endereco);
		} catch (DomainException de) {
			logger.error("Erro ao buscar CEP: {}", de.getMessage());
		}

		return "/login/index.html";
	}
}
