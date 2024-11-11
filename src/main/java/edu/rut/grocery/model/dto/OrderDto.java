package edu.rut.grocery.model.dto;

import edu.rut.grocery.model.domain.ProductOrder;

import java.util.Set;

public class OrderDto {

	private double price;
	private Set<ProductOrder> productOrders;

	public OrderDto(double price, Set<ProductOrder> productOrders) {
		this.price = price;
		this.productOrders = productOrders;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Set<ProductOrder> getProductOrders() {
		return productOrders;
	}

	public void setProductOrders(Set<ProductOrder> productOrders) {
		this.productOrders = productOrders;
	}
}
