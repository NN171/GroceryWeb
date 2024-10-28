package edu.rut.grocery.model.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "feedbacks")
public class Feedback extends BaseEntity {

	private int rating;
	private String comment;
	private Product product;
	private Customer customer;

	public Feedback() {
	}

	public Feedback(int rating, String comment, Product product, Customer customer) {
		this.rating = rating;
		this.comment = comment;
		this.product = product;
		this.customer = customer;
	}

	@Column(name = "rating")
	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	@Column(name = "comment")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@ManyToOne
	@JoinColumn(name = "product_id")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne
	@JoinColumn(name = "customer_id")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
