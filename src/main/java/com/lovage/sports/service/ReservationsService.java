package com.lovage.sports.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lovage.sports.domain.Field;
import com.lovage.sports.domain.Player;
import com.lovage.sports.domain.Reservation;
import com.lovage.sports.web.domain.CreateReservation;

@Service
public class ReservationsService {

	@Autowired
	private PlayersService playerService;

	@Autowired
	private FieldsService fieldsService;

	private List<Reservation> RESERVATIONS = null;

	public List<Reservation> getReservations() {
		if (RESERVATIONS == null) {
			RESERVATIONS = fillReservations();
		}
		return RESERVATIONS;
	}

	public Reservation book(Reservation reservation) {

		Player initiator = playerService.getAllPlayers().get(0);
		reservation.setId("34");
		reservation.setInitiator(initiator);
		reservation.setParticipants(new Player[] { initiator });

		RESERVATIONS.add(reservation);

		return reservation;
	}

	public Reservation book2(CreateReservation reservation) {

		Reservation newReservation = new Reservation();
		Player initiator = playerService.getAllPlayers().get(0);
		newReservation.setId("34");
		newReservation.setInitiator(initiator);
		newReservation.setParticipants(new Player[] { initiator });

		RESERVATIONS.add(newReservation);

		return newReservation;
	}

	public List<Reservation> getReservationsBetweenDates(LocalDateTime start, LocalDateTime end) {
		if (start == null || end == null) {
			return Arrays.asList();
		}

		return getReservations().stream().filter(r -> r.getStart().isBefore(end) && r.getEnd().isAfter(start))
				.collect(Collectors.toList());
	}

	private List<Reservation> fillReservations() {
		List<Player> players = playerService.getAllPlayers();
		List<Field> fields = fieldsService.getAllFields();

		Reservation r1 = new Reservation();
		r1.setId("31");
		r1.setField(fields.get(0));
		r1.setStart(LocalDateTime.of(2018, 11, 05, 20, 30));
		r1.setEnd(LocalDateTime.of(2018, 11, 05, 22, 00));
		r1.setInitiator(players.get(0));
		r1.setParticipants(new Player[] { players.get(0), players.get(1) });

		Reservation r2 = new Reservation();
		r2.setId("32");
		r2.setField(fields.get(0));
		r2.setStart(LocalDateTime.of(2018, 11, 05, 10, 30));
		r2.setEnd(LocalDateTime.of(2018, 11, 05, 12, 00));
		r2.setInitiator(players.get(2));
		r2.setParticipants(new Player[] { players.get(2) });

		Reservation r3 = new Reservation();
		r3.setId("33");
		r3.setField(fields.get(3));
		r3.setStart(LocalDateTime.of(2018, 11, 10, 19, 30));
		r3.setEnd(LocalDateTime.of(2018, 11, 10, 21, 00));
		r3.setInitiator(players.get(1));
		r3.setParticipants(new Player[] { players.get(0), players.get(1), players.get(2) });

		return Arrays.asList(r1, r2, r3);
	}

}
