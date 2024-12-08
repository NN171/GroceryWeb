package edu.rut.grocery.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "products_orders")
public class ProductOrder extends BaseEntity {

	private int quantity;
	private Order order;
	private Product product;

	public ProductOrder() {
	}

	public ProductOrder(int quantity, Order order, Product product) {
		this.quantity = quantity;
		this.order = order;
		this.product = product;
	}

	@Column(name = "quantity")
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
