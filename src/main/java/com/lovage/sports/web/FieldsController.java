package com.lovage.sports.web;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lovage.sports.domain.Field;
import com.lovage.sports.domain.Reservation;
import com.lovage.sports.service.FieldsService;
import com.lovage.sports.service.ReservationsService;

@RestController
@RequestMapping("/fields")
public class FieldsController {

	@Autowired
	private FieldsService service;

	@Autowired
	private ReservationsService reservationService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Field> getFields() {

		return service.getFields();

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Field getFieldById(@PathVariable("id") int id) {

		Field field = service.getFields().stream().filter(f -> f.getId() == id).findFirst().orElse(null);
		return field;

	}

	@RequestMapping(value = "/available", method = RequestMethod.GET)
	public List<Field> getAvailableFields(
			@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime start,
			@RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime end) {

		List<Reservation> reservationsBetweenDates = reservationService.getReservationsBetweenDates(start, end);
		List<Integer> fieldIdsForReservationsBetweenDates = reservationsBetweenDates.stream()
				.map(r -> r.getField().getId()).collect(Collectors.toList());

		return service.getFields().stream().filter(f -> !fieldIdsForReservationsBetweenDates.contains(f.getId()))
				.collect(Collectors.toList());

	}
}
