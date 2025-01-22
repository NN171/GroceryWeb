package edu.rut.grocery.dto;

import java.io.Serializable;
import java.util.Set;

public class OrderDto implements Serializable {
	private Long id;
	private String status;
	private Double price;
	private Set<ProductOrderDto> productOrders;

	public OrderDto() {
	}

	public OrderDto(Long id, String status, Double price, Set<ProductOrderDto> productOrders) {
		this.id = id;
		this.status = status;
		this.price = price;
		this.productOrders = productOrders;
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

	public Set<ProductOrderDto> getProductOrders() {
		return productOrders;
	}

	public void setProductOrders(Set<ProductOrderDto> productOrders) {
		this.productOrders = productOrders;
	}
}
