package edu.rut.grocery.domain;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

	private double price;
	private Customer customer;
	private Employee employee;
	private Set<ProductOrder> productOrders;

	public Order() {
	}

	public Order(double price, Customer customer, Employee employee, Set<ProductOrder> productOrders) {
		this.price = price;
		this.customer = customer;
		this.employee = employee;
		this.productOrders = productOrders;
	}

	@Column(name = "price")
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@ManyToOne
	@JoinColumn(name = "customer_id")
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@ManyToOne
	@JoinColumn(name = "employee_id")
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<ProductOrder> getProductOrders() {
		return productOrders;
	}

	public void setProductOrders(Set<ProductOrder> productOrders) {
		this.productOrders = productOrders;
	}
}
