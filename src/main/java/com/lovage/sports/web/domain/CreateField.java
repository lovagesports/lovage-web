package com.lovage.sports.web.domain;

public class CreateField {

	private String name;
	private String location;
	private int length;
	private int width;
	private int recommendedNumberOfPlayers;
	private int pricePerHour;

	@Override
	public String toString() {
		return "CreateField [name=" + name + ", location=" + location + ", length=" + length + ", width=" + width
				+ ", recommendedNumberOfPlayers=" + recommendedNumberOfPlayers + ", pricePerHour=" + pricePerHour + "]";
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

	public int getRecommendedNumberOfPlayers() {
		return recommendedNumberOfPlayers;
	}

	public void setRecommendedNumberOfPlayers(int recommendedNumberOfPlayers) {
		this.recommendedNumberOfPlayers = recommendedNumberOfPlayers;
	}

	public int getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(int pricePerHour) {
		this.pricePerHour = pricePerHour;
	}
}
