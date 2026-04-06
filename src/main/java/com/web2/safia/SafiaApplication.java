package com.web2.safia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.modulith.Modulithic;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableAsync
@EnableCaching
@EnableFeignClients
@EnableJpaAuditing
@EnableWebSecurity
@Modulithic
public class SafiaApplication {
	public static void main(String[] args) {
		var modules = ApplicationModules.of(SafiaApplication.class);
		modules.forEach(System.out::println);

		SpringApplication.run(SafiaApplication.class, args);
	}
}
