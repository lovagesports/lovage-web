package com.lovage.sports.service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lovage.sports.domain.Field;
import com.lovage.sports.domain.Player;
import com.lovage.sports.domain.Reservation;
import com.lovage.sports.domain.User;
import com.lovage.sports.repo.ReservationRepository;
import com.lovage.sports.web.domain.CreateReservation;

@Service
public class ReservationsService {

	@Autowired
	private FieldsService fieldsService;

	@Autowired
	private UserService userService;

	@Autowired
	private ReservationRepository reservationRepo;

	@Transactional
	public Reservation create(CreateReservation newReservation, Principal principal) {

		Optional<User> loggedInUser = userService.findUserByEmail(principal.getName());

		Reservation reservation = new Reservation();
		Player initiator = loggedInUser.get().getPlayer();
		Field field = fieldsService.getField(newReservation.getFieldId());
		Player[] participants = { initiator };
		reservation.setStart(newReservation.getStart());
		reservation.setEnd(newReservation.getStart().plusMinutes(newReservation.getDuration()));
		reservation.setDuration(newReservation.getDuration());
		reservation.setDate(newReservation.getStart().toLocalDate());
		reservation.setInitiator(initiator);
		reservation.setField(field);
		reservation.setParticipants(participants);

		Reservation savedReservation = reservationRepo.save(reservation);
		return savedReservation;
	}

	public List<Reservation> getReservations() {
		return reservationRepo.findAll();
	}

	public List<Reservation> getReservationsBetweenDates(LocalDateTime start, LocalDateTime end) {
		if (start == null || end == null) {
			return Arrays.asList();
		}

		return getReservations().stream().filter(r -> r.getStart().isBefore(end) && r.getEnd().isAfter(start))
				.collect(Collectors.toList());
	}
}
