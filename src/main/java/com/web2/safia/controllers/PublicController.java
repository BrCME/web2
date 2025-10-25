package com.web2.safia.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web2.safia.events.CommitEventPublisher;
import com.web2.safia.exceptions.DomainException;
import com.web2.safia.models.Address;
import com.web2.safia.models.Employee;
import com.web2.safia.services.EnderecoService;

@Controller
@RequestMapping("/public")
public class PublicController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	private final CommitEventPublisher commitEventPublisher;
	private final EnderecoService enderecoService;

	public PublicController(
			CommitEventPublisher commitEventPublisher,
			EnderecoService enderecoService) {

		this.commitEventPublisher = commitEventPublisher;
		this.enderecoService = enderecoService;
	}

	@GetMapping("/")
	public String getLandingPage() {
		return "index.html";
	}

	@GetMapping("sign-up")
	public String signUp() {
		commitEventPublisher.publishActivateCommitEvent("Tela de Sign Up", new Employee(UUID.randomUUID(), null, null, null, null, null, null, null, null, null, null));

		commitEventPublisher.publishDeactivateCommitEvent("Desativando tela de sign up", new Employee(UUID.randomUUID(), null, null, null, null, null, null, null, null, null, null));
		return "/login/signup.html";
	}

	@GetMapping("sign-in")
	public String signIn() {
		return "/login/signin.html";
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
