package edu.rut.grocery.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "products_orders")
public class ProductOrder extends BaseEntity {

	private Order order;
	private Product product;

	public ProductOrder() {
	}

	public ProductOrder(Order order, Product product) {
		this.order = order;
		this.product = product;
	}

	@ManyToOne
	@JoinColumn(name = "order_id")
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@ManyToOne
	@JoinColumn(name = "product_id")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
