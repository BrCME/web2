package com.web2.safia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableCaching
@EnableWebSecurity
@EnableFeignClients
public class SafiaApplication {
	public static void main(String[] args) {
		SpringApplication.run(SafiaApplication.class, args);
	}
}
