package com.web2.safia.shared.config;

import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import com.web2.safia.shared.entity.Employee;

@Configuration
public class SecurityConfig implements AuditorAware<Employee> {
	@Value("${custom.security.salt-length:20}")
	private int securitySaltLength;

	@Value("${custom.security.hash-length:44}")
	private int securityHashLength;

	@Value("${custom.security.parallelism:16}")
	private int securityParallelism;

	@Value("${custom.security.memory:16384}")
	private int securityMemory;

	@Value("${custom.security.iterations:16}")
	private int securityIterations;

	private static final String[] WHITE_LIST = { "/**", "/actuator", "/actuator/**", "/api/auth/sign-in",
			"/api/auth/sign-up", "/api/auth/sign-out", "/swagger-ui/index.html" };
	private static final String[] ADMIN_LIST = { "/api/teams/**", "/api/employees/**", "/api/commits/**",
			"/api/projects/**", "/api/works/**", "/api/tasks/**" };

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) {
		return http
				.authorizeHttpRequests(request -> request
						.requestMatchers(WHITE_LIST).permitAll()
						.requestMatchers(ADMIN_LIST).hasRole("ADMIN")
						// .anyRequest().authenticated()
						.anyRequest().permitAll())
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.cors(Customizer.withDefaults())
				// .csrf(csrf -> csrf
				// .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
				.csrf(csrf -> csrf.disable())
				// .oauth2ResourceServer(customizer ->
				// customizer.jwt(Customizer.withDefaults()))
				.build();
	}

	@Bean
	HttpSessionIdResolver sessionIdResolver() {
		return HeaderHttpSessionIdResolver.xAuthToken();
	}

	@Bean
	UserDetailsService userDetailsService(DataSource datasource) {
		var userDetailsManager = new JdbcUserDetailsManager(datasource);

		userDetailsManager.setUsersByUsernameQuery(
				"SELECT e.id, e.name, e.email, e.birth_date, (e.deleted_at IS NULL) FROM employee e WHERE e.email LIKE ?");

		userDetailsManager.setAuthoritiesByUsernameQuery(
				"SELECT e.id, e.email, r.type FROM employee e INNER JOIN role_to_employee rte ON rte.employee_id = e.id INNER JOIN role r ON rte.role_id = r.id WHERE e.email LIKE ?");

		userDetailsManager.setRolePrefix("ROLE_");

		return userDetailsManager;
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
