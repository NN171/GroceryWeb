package edu.rut.grocery.dto;

public class ProductDto {
	private Long id;
	private String name;
	private Double price;
	private Integer amount;
	private String prodDate;
	private String expiryDate;
	private Double avgRating;

	public ProductDto() {
	}

	public ProductDto(Long id, String name, Double price, Integer amount, String prodDate, String expiryDate, Double avgRating) {
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

	public String getProdDate() {
		return prodDate;
	}

	public void setProdDate(String prodDate) {
		this.prodDate = prodDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Double getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(Double avgRating) {
		this.avgRating = avgRating;
	}
}
