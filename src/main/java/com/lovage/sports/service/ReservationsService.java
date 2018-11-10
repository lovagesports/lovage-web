package com.lovage.sports.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lovage.sports.domain.Player;
import com.lovage.sports.domain.Reservation;

@Service
public class ReservationsService {

	public List<Reservation> getReservationsBetweenDates(LocalDateTime start, LocalDateTime end) {
		return getReservations().stream().filter(r -> r.getStart().isBefore(end) && r.getEnd().isAfter(start))
				.collect(Collectors.toList());
	}

	public List<Reservation> getReservations() {

		Player[] players = getPlayers();

		Reservation r1 = new Reservation();
		r1.setId(31);
		r1.setStart(LocalDateTime.of(2018, 11, 05, 20, 30));
		r1.setEnd(LocalDateTime.of(2018, 11, 05, 22, 00));
		r1.setInitiator(players[0]);
		r1.setParticipants(new Player[] { players[0], players[1] });

		Reservation r2 = new Reservation();
		r2.setId(32);
		r2.setStart(LocalDateTime.of(2018, 11, 05, 10, 30));
		r2.setEnd(LocalDateTime.of(2018, 11, 05, 12, 00));
		r2.setInitiator(players[2]);
		r2.setParticipants(new Player[] { players[2] });

		Reservation r3 = new Reservation();
		r3.setId(32);
		r3.setStart(LocalDateTime.of(2018, 11, 10, 19, 30));
		r3.setEnd(LocalDateTime.of(2018, 11, 10, 21, 00));
		r3.setInitiator(players[1]);
		r3.setParticipants(new Player[] { players[0], players[1], players[2] });

		return Arrays.asList(r1, r2, r3);
	}

	private Player[] getPlayers() {

		Player player1 = new Player();
		player1.setId(21);
		player1.setName("Tudor Chirila");

		Player player2 = new Player();
		player2.setId(22);
		player2.setName("Kazimir Obrenovic");

		Player player3 = new Player();
		player3.setId(23);
		player3.setName("Laur Marat");

		return new Player[] { player1, player2, player3 };
	}
}
