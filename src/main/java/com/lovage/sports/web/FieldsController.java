package com.lovage.sports.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lovage.sports.domain.Field;
import com.lovage.sports.service.FieldsService;

@RestController
@RequestMapping("/fields")
public class FieldsController {

	@Autowired
	private FieldsService service;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Field> getFields() {

		return service.getFields();

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Field getFieldById(@PathVariable("id") int id) {

		Field field = service.getFields().stream().filter(f -> f.getId() == id).findFirst().orElse(null);
		return field;

	}

	// @RequestMapping(value = "/", method = RequestMethod.GET)
	// public List<Reservation> getReservations(
	// @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	// LocalDateTime start,
	// @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	// LocalDateTime end) {
	//
	// return service.getReservationsBetweenDates(start, end);
	//
	// }
}
