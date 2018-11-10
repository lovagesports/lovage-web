package com.lovage.sports.web;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lovage.sports.domain.Reservation;
import com.lovage.sports.service.ReservationsService;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {

	@Autowired
	private ReservationsService service;

	// @RequestMapping(value = "/", method = RequestMethod.GET)
	// public List<Reservation> getReservations() {
	//
	// return service.getReservations();
	//
	// }

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<Reservation> getReservations(
			@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
			@RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

		return service.getReservationsBetweenDates(start, end);

	}
}
