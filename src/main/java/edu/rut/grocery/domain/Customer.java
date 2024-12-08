package edu.rut.grocery.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {

	private String firstName;
	private String lastName;
	private String phoneNumber;
	private double ordersAmount;
	private int discount;
	private Set<Feedback> feedbacks;
	private Set<Order> orders;

	public Customer() {
	}

	public Customer(String firstName, String lastName, String phoneNumber, double ordersAmount, int discount, Set<Feedback> feedbacks, Set<Order> orders) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.ordersAmount = ordersAmount;
		this.discount = discount;
		this.feedbacks = feedbacks;
		this.orders = orders;
	}

	@Column(name = "first_name")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "phone_number")
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "orders_amount")
	public double getOrdersAmount() {
		return ordersAmount;
	}

	public void setOrdersAmount(double ordersAmount) {
		this.ordersAmount = ordersAmount;
	}

	@Column(name = "discount")
	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<Feedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(Set<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
}
