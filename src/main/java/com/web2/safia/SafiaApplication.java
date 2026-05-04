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

import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;

@SpringBootApplication
@EnableAsync
@EnableCaching
@EnableFeignClients
@EnableJpaAuditing
@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
@EnableWebSecurity
@Modulithic
public class SafiaApplication {
	public static void main(String[] args) {
		var modules = ApplicationModules
				.of(SafiaApplication.class);

		modules.forEach(System.out::println);
		modules.detectViolations();

		SpringApplication.run(SafiaApplication.class, args);
	}
}
