package com.lovage.sports.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lovage.sports.domain.Field;
import com.lovage.sports.repo.FieldRepository;
import com.lovage.sports.web.domain.CreateField;

@Service
public class FieldsService {

	@Autowired
	private FieldRepository fieldRepository;

	@Transactional
	public Field createField(CreateField createField) {

		Field field = new Field();
		field.setName(createField.getName());
		field.setLocation(createField.getLocation());
		field.setLength(createField.getLength());
		field.setWidth(createField.getWidth());
		field.setPricePerHour(createField.getPricePerHour());
		field.setRecommendedPlayers(createField.getRecommendedNumberOfPlayers());

		Field savedField = fieldRepository.save(field);
		return savedField;
	}

	public List<Field> getAllFields() {

		return fieldRepository.findAll();
	}

	public Field getField(String id) {

		Field found = fieldRepository.findById(id).orElse(null);
		return found;
	}
}
