package edu.rut.grocery.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class ProductDto implements Serializable {
	private Long id;
	private String name;
	private Double price;
	private Integer amount;
	private LocalDate prodDate;
	private LocalDate expiryDate;
	private Double avgRating;

	public ProductDto() {
	}

	public ProductDto(Long id, String name, Double price, Integer amount, LocalDate prodDate, LocalDate expiryDate, Double avgRating) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.amount = amount;
		this.prodDate = prodDate;
		this.expiryDate = expiryDate;
		this.avgRating = avgRating;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public LocalDate getProdDate() {
		return prodDate;
	}

	public void setProdDate(LocalDate prodDate) {
		this.prodDate = prodDate;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Double getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(Double avgRating) {
		this.avgRating = avgRating;
	}
}
