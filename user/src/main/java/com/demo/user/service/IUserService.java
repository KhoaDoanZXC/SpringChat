package com.demo.user.service;

import com.demo.user.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {
	List<User> getAllUsers();

	User findUserByUsername(String username);
}
