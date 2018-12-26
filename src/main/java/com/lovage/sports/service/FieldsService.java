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

	// Field f2 = new Field();
	// f2.setId("12");
	// f2.setName("Teren 1");
	// f2.setLocation("Turzii");
	// f2.setPricePerHour(80);
	// f2.setLength(37);
	// f2.setWidth(21);
	// f2.setRecommendedPlayers(14);
	//
	// Field f3 = new Field();
	// f3.setId("13");
	// f3.setName("Teren Forbal 1");
	// f3.setLocation("Baza Transilvania");
	// f3.setPricePerHour(100);
	// f3.setLength(50);
	// f3.setWidth(23);
	// f3.setRecommendedPlayers(16);
	//
	// Field f4 = new Field();
	// f4.setId("14");
	// f4.setName("");
	// f4.setLocation("Baza gheorgheni");
	// f4.setPricePerHour(120);
	// f4.setLength(40);
	// f4.setWidth(20);
	// f4.setRecommendedPlayers(12);
	//
	// Field f5 = new Field();
	// f5.setId("15");
	// f5.setName("Baba novac");
	// f5.setLocation("Baba novac");
	// f5.setPricePerHour(160);
	// f5.setLength(44);
	// f5.setWidth(24);
	// f5.setRecommendedPlayers(14);
	//
	// Field f6 = new Field();
	// f6.setId("16");
	// f6.setName("Teren mic");
	// f6.setLocation("Cotton");
	// f6.setPricePerHour(120);
	// f6.setLength(35);
	// f6.setWidth(18);
	// f6.setRecommendedPlayers(12);
	//
	// Field f7 = new Field();
	// f7.setId("17");
	// f7.setName("");
	// f7.setLocation("Cora");
	// f7.setPricePerHour(100);
	// f7.setLength(38);
	// f7.setWidth(21);
	// f7.setRecommendedPlayers(14);
	//
	// Field f8 = new Field();
	// f8.setId("18");
	// f8.setName("Padin");
	// f8.setLocation("Padin");
	// f8.setPricePerHour(120);
	// f8.setLength(39);
	// f8.setWidth(22);
	// f8.setRecommendedPlayers(14);
	//
	// Field f9 = new Field();
	// f9.setId("19");
	// f9.setName("Teren Forbal 2");
	// f9.setLocation("Baza Transilvania");
	// f9.setPricePerHour(150);
	// f9.setLength(43);
	// f9.setWidth(22);
	// f9.setRecommendedPlayers(14);
}
