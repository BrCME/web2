package com.web2.safia.shared.config;

import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPublicKey;
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
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import com.web2.safia.shared.entity.Employee;

@Configuration
public class SecurityConfig implements AuditorAware<Employee> {
	// @Value("${custom.jwt.key.location}")
	// private RSAPublicKey publicRsaKey;

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

	private static final String[] WHITE_LIST = { "/actuator", "/actuator/**", "/api/auth/*",
			"/v3/api-docs/swagger-config", "/swagger-ui/index.html", "/v3/api-docs",
			"/swagger-ui-bundle.js", "/swagger-ui-standalone-preset.js", "/swagger-initializer.js" };
	private static final String[] ADMIN_LIST = { "/api/teams/**", "/api/employees/**", "/api/commits/**",
			"/api/projects/**", "/api/works/**", "/api/tasks/**" };

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) {
		return http
				.authorizeHttpRequests(request -> request
						.requestMatchers(WHITE_LIST).permitAll()
						.requestMatchers(ADMIN_LIST).hasRole("ADMIN")
						.anyRequest().permitAll())
						// .anyRequest().authenticated())
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.cors(Customizer.withDefaults())
				// .csrf(csrf -> csrf
				// .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
				.csrf(csrf -> csrf.disable())
				// .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
				.oauth2AuthorizationServer(oauth2 -> oauth2.oidc(Customizer.withDefaults()))
				.build();
	}

	// @Bean
	// JwtDecoder jwtDecoder() {
	// return NimbusJwtDecoder.withPublicKey(publicRsaKey).build();
	// }

	@Bean
	HttpSessionIdResolver sessionIdResolver() {
		return HeaderHttpSessionIdResolver.xAuthToken();
	}

	@Bean
	UserDetailsService userDetailsService(DataSource datasource) {
		var userDetailsManager = new JdbcUserDetailsManager(datasource);

		userDetailsManager.setUsersByUsernameQuery("""
					SELECT u.id, u.username, (u.deleted_at IS NULL)
					FROM auth."user" u
					WHERE u.username LIKE ?
				""");

		userDetailsManager.setAuthoritiesByUsernameQuery("""
					SELECT u.id, u.username, r.type
					FROM auth."user" u
						INNER JOIN auth."role_to_user" rtu ON rtu.user_id = u.id
						INNER JOIN auth."role" r ON rtu.role_id = r.id
					WHERE u.username LIKE ?"
				""");

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
