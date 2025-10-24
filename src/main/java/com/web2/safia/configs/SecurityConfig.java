package com.web2.safia.configs;

import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import io.micrometer.common.lang.NonNull;

@Configuration
public class SecurityConfig implements AuditorAware<UUID>{
	private static final String[] WHITE_LIST = { "/**" };
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(authorize -> authorize.requestMatchers(WHITE_LIST).permitAll())
				.formLogin(form -> form.loginPage("/login").permitAll())
				.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public @NonNull Optional<UUID> getCurrentAuditor() {
		return Optional.of(UUID.randomUUID());
		
		// return Optional
		// 	.ofNullable(SecurityContextHolder.getContext())
		// 	.map(SecurityContext::getAuthentication)
		// 	.filter(Authentication::isAuthenticated)
		// 	.map(Authentication::getPrincipal)
		// 	.map(Employee.class::cast)
		// 	.map(Employee::getId);
	}
}
