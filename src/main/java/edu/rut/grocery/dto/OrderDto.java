package edu.rut.grocery.dto;

import java.io.Serializable;
import java.util.Set;

public class OrderDto implements Serializable {
	private Long id;
	private String status;
	private Double price;
	private Set<OrderProductDto> products;

	public OrderDto() {
	}

	public OrderDto(Long id, String status, Double price, Set<OrderProductDto> products) {
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

	public Set<OrderProductDto> getProducts() {
		return products;
	}

	public void setProducts(Set<OrderProductDto> products) {
		this.products = products;
	}
}
