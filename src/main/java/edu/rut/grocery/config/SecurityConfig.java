package edu.rut.grocery.config;

import edu.rut.grocery.domain.Role;
import edu.rut.grocery.repository.UserRepository;
import edu.rut.grocery.service.serviceImpl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

public class SecurityConfig {

	private final UserRepository userRepository;

	public SecurityConfig(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, SecurityContextRepository securityContextRepository) throws Exception {
		http
				.authorizeHttpRequests(
						auth ->
								auth.
										requestMatchers("/users/login", "/users/register").permitAll()
										.requestMatchers("/products/", "/stores/", "/employees/").authenticated()
										.requestMatchers("/stores/delete/", "/customers/delete/", "/employees/delete/")
										.hasRole(Role.ADMIN.name())
										.anyRequest().authenticated()
				)
				.formLogin(
						formLogin ->
								formLogin
										.loginPage("/users/login")
										.usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
										.usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
										.defaultSuccessUrl("/")
										.failureForwardUrl("/user/login")
				)
				.logout(
						logout ->
								logout.logoutUrl("/users/logout")
										.logoutSuccessUrl("/")
										.invalidateHttpSession(true)
				).securityContext(
						securityContext -> securityContext.securityContextRepository(securityContextRepository)
				);

		return http.build();
	}

	@Bean
	public SecurityContextRepository securityContextRepository() {
		return new DelegatingSecurityContextRepository(
				new RequestAttributeSecurityContextRepository(),
				new HttpSessionSecurityContextRepository()
		);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl(userRepository);
	}
}
