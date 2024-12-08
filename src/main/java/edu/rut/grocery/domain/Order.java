package edu.rut.grocery.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

	private String status;
	private double price;
	private LocalDateTime createDate;
	private Customer customer;
	private Employee employee;
	private Set<ProductOrder> productOrders;

	public Order() {
	}

	public Order(String status, double price, LocalDateTime createDate, Customer customer, Employee employee, Set<ProductOrder> productOrders) {
		this.status = status;
		this.price = price;
		this.createDate = createDate;
		this.customer = customer;
		this.employee = employee;
		this.productOrders = productOrders;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Column(name = "create_date")
	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
}
