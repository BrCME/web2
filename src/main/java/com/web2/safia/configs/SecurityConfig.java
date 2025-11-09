package com.web2.safia.configs;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.web2.safia.models.Employee;

@Configuration
public class SecurityConfig implements AuditorAware<Employee> {
	private static final String[] WHITE_LIST = { "/index.html", "/", "/logout", "/login", "/auth/sign-in",
			"/auth/sign-up", "/auth/new-employee" };
	private static final String[] CONTENT_LIST = { "/images/**", "/svgs/**", "/scripts/**" };

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(customizer -> customizer
						.requestMatchers(WHITE_LIST).permitAll()
						.requestMatchers(CONTENT_LIST).permitAll()
						.anyRequest().authenticated())
				.formLogin(form -> form
						.loginPage("/auth/sign-in").permitAll()
						.defaultSuccessUrl("/").permitAll())
				.logout(logout -> logout
						.logoutUrl("/auth/sign-out").permitAll()
						.invalidateHttpSession(true)
						.logoutSuccessUrl("/")
						.clearAuthentication(true)
						.deleteCookies("JSESSIONID").permitAll())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
				.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public @NonNull Optional<Employee> getCurrentAuditor() {
		return Optional
				.ofNullable(SecurityContextHolder.getContext())
				.map(SecurityContext::getAuthentication)
				.filter(Authentication::isAuthenticated)
				.map(Authentication::getPrincipal)
				.map(Employee.class::cast);
	}
}
