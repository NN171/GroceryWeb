package edu.rut.grocery.model.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "products_orders")
public class ProductOrder extends BaseEntity {

	private Order order;
	private Product product;

	public ProductOrder() {
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
