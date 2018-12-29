package com.lovage.sports.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lovage.sports.domain.Field;
import com.lovage.sports.service.FieldsService;
import com.lovage.sports.web.domain.CreateField;

@RestController
@RequestMapping("/auth/fields")
public class FieldsController {

	@Autowired
	private FieldsService fieldService;

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Field> getFields() {

		return fieldService.getAllFields();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Field> getFieldById(@PathVariable("id") String id) {

		Field field = fieldService.getField(id);
		if (field == null) {
			return new ResponseEntity<Field>(field, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Field>(field, HttpStatus.FOUND);
		}
	}

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<CreateField> createNewField(@RequestBody @Valid CreateField field) {

		Field createdField = createField(field);

		if (createdField == null) {
			return new ResponseEntity<CreateField>(field, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<CreateField>(field, HttpStatus.CREATED);
		}
	}

	private Field createField(CreateField field) {
		Field created = fieldService.create(field);
		return created;
	}
}
