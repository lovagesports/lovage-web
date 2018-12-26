package com.lovage.sports.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reservations")
public class Reservation {

	@Id
	private String id;

	@DBRef
	private Field field;

	private LocalDateTime start;
	private LocalDateTime end;
	private LocalDate date;

	private int duration;

	@DBRef
	private Player initiator;

	@DBRef
	private Player[] participants;

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", field=" + field + ", start=" + start + ", end=" + end + ", date=" + date
				+ ", duration=" + duration + ", initiator=" + initiator + ", participants="
				+ Arrays.toString(participants) + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Player getInitiator() {
		return initiator;
	}

	public void setInitiator(Player initiator) {
		this.initiator = initiator;
	}

	public Player[] getParticipants() {
		return participants;
	}

	public void setParticipants(Player[] participants) {
		this.participants = participants;
	}
}
