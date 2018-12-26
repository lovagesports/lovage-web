package com.lovage.sports.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lovage.sports.domain.Reservation;

public interface ReservationRepository extends MongoRepository<Reservation, String> {

}
