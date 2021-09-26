package com.demo.user.repository;

import com.demo.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends MongoRepository<User, String> {
	User findUserByUsername(String username);
}
