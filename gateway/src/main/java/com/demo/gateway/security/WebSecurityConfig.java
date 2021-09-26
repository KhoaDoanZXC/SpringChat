package com.demo.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final String REGISTRATION_PATH;
	private final String LOGIN_PATH;
	private final String ACTUATOR_PATH;
	private final Environment env;

	@Autowired
	public WebSecurityConfig(Environment env) {
		this.env = env;
		this.REGISTRATION_PATH = env.getProperty("api.registration.url.path");
		this.LOGIN_PATH = env.getProperty("api.login.url.path");
		this.ACTUATOR_PATH = env.getProperty("api.zuul.actuator.url.path");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.headers().frameOptions().disable()
				.and()
				.authorizeRequests()
				.antMatchers(ACTUATOR_PATH).permitAll()
				.antMatchers("/user/api/user/login").permitAll()
				.anyRequest().authenticated()
				.and()
				.addFilter(new JWTAuthenticationFilter(authenticationManager(), env))
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}
