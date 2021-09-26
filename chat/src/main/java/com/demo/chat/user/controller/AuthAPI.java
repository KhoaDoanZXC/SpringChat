package com.demo.chat.user.controller;

import com.demo.chat.user.model.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/auth")
public class AuthAPI {
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	private HttpHeaders responseHeaders = new HttpHeaders();

	AuthAPI() {
	}

	@PostMapping(path = "/login")
	public ResponseEntity<String> doLogin(@RequestBody LoginRequest loginRequest) {
		String username = loginRequest.getUsername();
		if (username.isEmpty())
			return ResponseEntity.status(403).body("Bad");

		responseHeaders.set("Authorization", username);
		responseHeaders.setAccessControlExposeHeaders(Arrays.asList(new String[]{"Authorization"}));


		return ResponseEntity.status(200).headers(responseHeaders).body("loggedin");
	}
}
