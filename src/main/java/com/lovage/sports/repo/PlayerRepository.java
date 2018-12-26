package com.lovage.sports.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lovage.sports.domain.Player;

public interface PlayerRepository extends MongoRepository<Player, String> {

}
