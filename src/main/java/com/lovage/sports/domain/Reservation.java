package com.lovage.sports.domain;

import java.time.LocalDateTime;

public class Reservation {

	private int id;
	private Field field;
	private LocalDateTime start;
	private LocalDateTime end;
	private String time;
	private int duration;
	private Player initiator;
	private Player[] participants;

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
