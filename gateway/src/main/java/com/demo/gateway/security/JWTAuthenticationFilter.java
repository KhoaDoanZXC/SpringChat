package com.demo.gateway.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
	private static final Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
	private final String TOKEN_HEADER_NAME;
	private final String TOKEN_HEADER_PREFIX;
	private final String TOKEN_SECRET;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, Environment env) {
		super(authenticationManager);
		this.TOKEN_HEADER_NAME = env.getProperty("authorization.token.header.name");
		this.TOKEN_HEADER_PREFIX = env.getProperty("authorization.token.header.prefix");
		this.TOKEN_SECRET = env.getProperty("token.secret");
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
		final String authorizationHeader = req.getHeader(TOKEN_HEADER_NAME);

		if(authorizationHeader == null) {
			return null;
		}

		final String token = authorizationHeader.replace(TOKEN_HEADER_PREFIX, "");
		String userId = null;
		try {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC512(TOKEN_SECRET)).build();
			DecodedJWT jwt = verifier.verify(token);
			userId = jwt.getSubject();
		} catch (JWTVerificationException e) {
			throw new InvalidClaimException("Invalid token");
		}

		return userId == null ? null : new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String authorizationHeader = request.getHeader(TOKEN_HEADER_NAME);

		if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_HEADER_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		chain.doFilter(request, response);
	}
}
