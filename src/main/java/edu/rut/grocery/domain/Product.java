package edu.rut.grocery.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

	private String name;
	private Double price;
	private Integer amount;
	private LocalDateTime prodDate;
	private LocalDateTime expiryDate;
	private Double avgRating;
	private Set<Feedback> feedbacks;
	private Set<ProductOrder> productOrders;

	public Product() {
	}

	public Product(String name, Double price, Integer amount, LocalDateTime prodDate, LocalDateTime expiryDate, Double avgRating, Set<Feedback> feedbacks, Set<ProductOrder> productOrders) {
		this.name = name;
		this.price = price;
		this.amount = amount;
		this.prodDate = prodDate;
		this.expiryDate = expiryDate;
		this.avgRating = avgRating;
		this.feedbacks = feedbacks;
		this.productOrders = productOrders;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "price")
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Column(name = "amount")
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Column(name = "prod_date")
	public LocalDateTime getProdDate() {
		return prodDate;
	}

	public void setProdDate(LocalDateTime prodDate) {
		this.prodDate = prodDate;
	}

	@Column(name = "expiry_date")
	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Column(name = "avg_rating")
	public double getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<Feedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(Set<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<ProductOrder> getProductOrders() {
		return productOrders;
	}

	public void setProductOrders(Set<ProductOrder> productOrders) {
		this.productOrders = productOrders;
	}
}
