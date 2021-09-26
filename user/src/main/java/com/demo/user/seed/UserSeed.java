package com.demo.user.seed;

import com.demo.user.model.User;
import com.demo.user.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserSeed {
	private final IUserRepo userRepo;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	UserSeed(IUserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepo = userRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@PostConstruct
	private void insertUser() {
		List<User> users = new ArrayList<>();

		if (userRepo.findUserByUsername("khoa") == null)
			users.add(new User("khoa", bCryptPasswordEncoder.encode("123")));
		if (userRepo.findUserByUsername("magus") == null)
			users.add(new User("magus", bCryptPasswordEncoder.encode("321")));

		userRepo.saveAll(users);
	}
}
