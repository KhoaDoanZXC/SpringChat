package com.demo.user.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.demo.user.model.LoginRequest;
import com.demo.user.model.User;
import com.demo.user.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final Environment env;
	private final IUserService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	JWTAuthenticationFilter(Environment env, IUserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.env = env;
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	private void verifyLoginDetails(LoginRequest loginRequest) throws UsernameNotFoundException, BadCredentialsException {
		String username = loginRequest.getUsername();
		String password = loginRequest.getPassword();

		UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		boolean passwordMatched = bCryptPasswordEncoder.matches(password, userDetails.getPassword());
		if (!passwordMatched) throw new BadCredentialsException("Incorrect password for " + username);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			LoginRequest loginDetails = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
			verifyLoginDetails(loginDetails);

			return new UsernamePasswordAuthenticationToken(loginDetails.getUsername(), loginDetails.getPassword(), new ArrayList<>());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
			throws IOException, ServletException {
		String userId = userDetailsService.findUserByUsername(authResult.getName()).getId();

		String token = JWT.create()
				.withSubject(userId)
				.withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration"))))
				.sign(Algorithm.HMAC512(env.getProperty("token.secret")));

		response.addHeader("token", token);
	}
}
