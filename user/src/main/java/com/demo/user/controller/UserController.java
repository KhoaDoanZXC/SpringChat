package com.demo.user.controller;

import com.demo.user.model.User;
import com.demo.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/api/user")
@RestController
public class UserController {
	private final IUserService userService;

	@Autowired
	UserController(IUserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAll() {
		return ResponseEntity.status(200).body(userService.getAllUsers());
	}
}
