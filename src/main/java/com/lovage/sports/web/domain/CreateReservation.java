package com.lovage.sports.web.domain;

import java.time.LocalDateTime;

public class CreateReservation {

	private int fieldId;
	private LocalDateTime start;
	private String startTime;
	private int duration;
	private int initiatorId;

	@Override
	public String toString() {
		return "CreateReservation [fieldId=" + fieldId + ", start=" + start + ", startTime=" + startTime + ", duration="
				+ duration + ", initiatorId=" + initiatorId + "]";
	}

	public int getFieldId() {
		return fieldId;
	}

	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getInitiatorId() {
		return initiatorId;
	}

	public void setInitiatorId(int initiatorId) {
		this.initiatorId = initiatorId;
	}
}
