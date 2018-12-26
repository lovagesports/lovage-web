package com.lovage.sports.web.domain;

import java.time.LocalDateTime;

public class CreateReservation {

	private String fieldId;
	private LocalDateTime start;
	private int duration;

	@Override
	public String toString() {
		return "CreateReservation [fieldId=" + fieldId + ", start=" + start + ", duration=" + duration + "]";
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}
