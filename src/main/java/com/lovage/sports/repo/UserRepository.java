package com.lovage.sports.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lovage.sports.domain.User;

public interface UserRepository extends MongoRepository<User, String> {

	User findByEmail(String email);
}
