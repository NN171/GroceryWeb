package edu.rut.grocery.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements Serializable {

	private String username;
	private String password;
	private Role role;
	private Customer customer;

	public User() {
	}

	public User(String username, String password, Role role, Customer customer) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.customer = customer;
	}

	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "role")
	@Enumerated(value = EnumType.STRING)
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
