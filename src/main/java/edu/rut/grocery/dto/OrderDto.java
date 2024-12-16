package edu.rut.grocery.dto;

import java.util.List;

public class OrderDto {
	private Long id;
	private String status;
	private Double price;
	private List<OrderProductDto> products;

	public OrderDto() {
	}

	public OrderDto(Long id, String status, Double price, List<OrderProductDto> products) {
		this.id = id;
		this.status = status;
		this.price = price;
		this.products = products;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public List<OrderProductDto> getProducts() {
		return products;
	}

	public void setProducts(List<OrderProductDto> products) {
		this.products = products;
	}
}
