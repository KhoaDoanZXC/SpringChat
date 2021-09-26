package com.demo.user.security;

import com.demo.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final Environment env;
	private final IUserService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	WebSecurityConfig(IUserService userService, Environment env, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.env = env;
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	private JWTAuthenticationFilter getJWTAuthentcationFilter() {
		JWTAuthenticationFilter filter = new JWTAuthenticationFilter(env, userService, bCryptPasswordEncoder);
		filter.setFilterProcessesUrl("/api/user/login");
		return filter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http
			.authorizeRequests()
//			.antMatchers("/api/user/login").permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilter(getJWTAuthentcationFilter());

		http.headers().frameOptions().disable();
	}
}
