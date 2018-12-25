package com.lovage.sports.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lovage.sports.domain.Role;

public interface RoleRepository extends MongoRepository<Role, String> {

	Role findByRole(String role);
}