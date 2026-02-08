package com.web2.safia.configs;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.web2.safia.employee.Employee;

@Configuration
public class SecurityConfig implements AuditorAware<Employee> {
	private static final String[] WHITE_LIST = { "/api/auth/employee", "/api/auth/sign-in", "/api/auth/sign-up",
			"/api/auth/sign-out" };
	private static final String[] ADMIN_LIST = { "/api/teams/", "/api/employee/", "/api/commit/",
			"/api/project/", "/api/work/", "/api/task/" };

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(customizer -> customizer
						.requestMatchers(WHITE_LIST).permitAll()
						.requestMatchers(ADMIN_LIST).hasRole("ADMIN")
						.anyRequest().authenticated())
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
		return new BCryptPasswordEncoder();
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
