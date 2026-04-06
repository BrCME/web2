package com.web2.safia.configs;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.web2.safia.employee.Employee;

@Configuration
public class SecurityConfig implements AuditorAware<Employee> {
	@Value("${security.salt-length:20}")
	private int securitySaltLength;

	@Value("${security.hash-length:44}")
	private int securityHashLength;

	@Value("${security.parallelism:32}")
	private int securityParallelism;

	@Value("${security.memory:16777216}")
	private int securityMemory;

	@Value("${security.iterations:16}")
	private int securityIterations;

	private static final String[] WHITE_LIST = { "/**", "/actuator", "/actuator/**", "/swagger-ui/**",
			"/api/auth/employee", "/api/auth/sign-in", "/api/auth/sign-up", "/api/auth/sign-out",
			"/swagger-ui/index.html" };
	private static final String[] ADMIN_LIST = { "/api/teams/**", "/api/employees/**", "/api/commits/**",
			"/api/projects/**", "/api/works/**", "/api/tasks/**" };

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(customizer -> customizer
						.requestMatchers(WHITE_LIST).permitAll()
						// .requestMatchers(ADMIN_LIST).hasRole("ADMIN")
						// .anyRequest().authenticated()
						.anyRequest().permitAll())
				// .formLogin(form -> form
				// .loginPage("/auth/sign-in").permitAll()
				// .defaultSuccessUrl("/").permitAll())
				// .logout(logout -> logout
				// .logoutUrl("/auth/sign-out").permitAll()
				// .invalidateHttpSession(true)
				// .clearAuthentication(true)
				// .deleteCookies("JSESSIONID")
				// .logoutSuccessUrl("/").permitAll())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
				.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new Argon2PasswordEncoder(
				securitySaltLength,
				securityHashLength,
				securityParallelism,
				securityMemory,
				securityIterations);
	}

	@Override
	public Optional<Employee> getCurrentAuditor() {
		return Optional
				.ofNullable(SecurityContextHolder.getContext())
				.map(SecurityContext::getAuthentication)
				.filter(Authentication::isAuthenticated)
				.map(Authentication::getPrincipal)
				.map(Employee.class::cast);
	}
}
