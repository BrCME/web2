package com.web2.sofia.sofia.controllers;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web2.sofia.sofia.models.Fazenda;

@Controller
@RequestMapping("/public")
public class PublicController {
	private final Logger logger = LoggerFactory.getLogger(PublicController.class);
	
	@Cacheable(cacheNames = "fazendinha", value = "fazendinha", key = "#nome")
	@GetMapping("test")
	public Fazenda teste() {
		var fazenda = new Fazenda("Óios D'Água");
		logger.info("Tem alguem tentando fazer um teste maroto: {}", fazenda);
		return fazenda;
	}
}
