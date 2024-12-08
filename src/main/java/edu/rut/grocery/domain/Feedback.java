package edu.rut.grocery.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedbacks")
public class Feedback extends BaseEntity {

	private int rating;
	private String comment;
	private LocalDateTime createDate;
	private Product product;
	private Customer customer;

	public Feedback() {
	}

	public Feedback(int rating, String comment, LocalDateTime createDate, Product product, Customer customer) {
		this.rating = rating;
		this.comment = comment;
		this.createDate = createDate;
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

	@Column(name = "create_date")
	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
}
