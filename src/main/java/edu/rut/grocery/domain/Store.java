package edu.rut.grocery.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "stores")
public class Store extends BaseEntity implements Serializable {

	private String address;
	private int employeesNum;
	private double soldAmount;
	private Set<Employee> employees;

	public Store() {
	}

	public Store(String address, int employeesNum, double soldAmount, Set<Employee> employees) {
		this.address = address;
		this.employeesNum = employeesNum;
		this.soldAmount = soldAmount;
		this.employees = employees;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "employees_num")
	public int getEmployeesNum() {
		return employeesNum;
	}

	public void setEmployeesNum(int employeesNum) {
		this.employeesNum = employeesNum;
	}

	@Column(name = "sold_amount")
	public double getSoldAmount() {
		return soldAmount;
	}

	public void setSoldAmount(double soldAmount) {
		this.soldAmount = soldAmount;
	}

	@OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}
}
