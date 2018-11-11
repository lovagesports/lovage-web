package com.lovage.sports.domain;

public class Field {

	private int id;
	private String name;
	private String location;
	private int length;
	private int width;
	private int recommendedPlayers;
	private int pricePerHour;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getRecommendedPlayers() {
		return recommendedPlayers;
	}

	public void setRecommendedPlayers(int recommendedPlayers) {
		this.recommendedPlayers = recommendedPlayers;
	}

	public int getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(int pricePerHour) {
		this.pricePerHour = pricePerHour;
	}
}