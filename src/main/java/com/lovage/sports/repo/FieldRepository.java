package com.lovage.sports.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lovage.sports.domain.Field;

public interface FieldRepository extends MongoRepository<Field, String> {

}
