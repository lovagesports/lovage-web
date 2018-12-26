package com.lovage.sports.domain;

import java.time.LocalDateTime;

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
	private String time;
	private int duration;

	@DBRef
	private Player initiator;

	@DBRef
	private Player[] participants;

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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}
