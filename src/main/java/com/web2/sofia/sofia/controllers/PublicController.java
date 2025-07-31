package com.web2.sofia.sofia.controllers;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web2.sofia.sofia.models.Fazenda;

@Controller
@RequestMapping("/public")
public class PublicController {
	private final Logger logger = LoggerFactory.getLogger(PublicController.class);

	@Cacheable(value = "teste", key = "'minha-key-privada'")
	@GetMapping("test")
	public Fazenda teste() {
		var fazenda = new Fazenda("Óios D'Água");
		logger.info("Tem alguem tentando fazer um teste maroto: {}", fazenda);
		return fazenda;
	}

	@Cacheable(value = "testeziho", key = "{ #nome }")
	@GetMapping("test2")
	public String teste2(String nome, String telefone) {
		var fazenda = new Fazenda("Teoricamente nada!");
		return "/login/index.html";
	}
}
