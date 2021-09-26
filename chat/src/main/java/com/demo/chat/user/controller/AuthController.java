package com.demo.chat.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthController {
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	@GetMapping(path = "/login")
	public String showLoginPage() {
		return "login";
	}

	@PostMapping(path = "/login")
	public String doLogin(HttpServletRequest request, @RequestParam(defaultValue = "") String username) {
		username = username.trim();

		log.info(username);

		if (username.isEmpty()) return "login";
		request.getSession().setAttribute("username", username);

		return "redirect:/rooms";
	}

	@GetMapping(path = "/logout")
	public String logout(HttpServletRequest request) {
		request.getSession(true).invalidate();

		return "redirect:/login";
	}
}
