package edu.rut.grocery.dto;

import java.io.Serializable;

public class HighRatedDto implements Serializable {
	private String name;
	private double price;
	private Integer avgRating;

	public HighRatedDto() {
	}

	public HighRatedDto(String name, double price, Integer avgRating) {
		this.name = name;
		this.price = price;
		this.avgRating = avgRating;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(Integer avgRating) {
		this.avgRating = avgRating;
	}
}
