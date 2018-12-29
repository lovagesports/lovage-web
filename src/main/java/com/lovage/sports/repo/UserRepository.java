package com.lovage.sports.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lovage.sports.domain.User;

public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> findByEmail(String email);
}
