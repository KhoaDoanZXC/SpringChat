package com.demo.user.service;

import com.demo.user.model.User;
import com.demo.user.repository.IUserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
	private final Logger log = LoggerFactory.getLogger(UserService.class);
	private final IUserRepo userRepo;

	@Autowired
	UserService(IUserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public User findUserByUsername(String username) {
		return userRepo.findUserByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	 	User user = findUserByUsername(username);
		if (user == null) throw new UsernameNotFoundException(username);
		return user;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
}
