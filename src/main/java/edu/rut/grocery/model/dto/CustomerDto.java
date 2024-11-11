package edu.rut.grocery.model.dto;

public class CustomerDto {

	private String firstName;
	private String lastName;
	private String phoneNumber;
	private double ordersAmount;
	private int discount;

	public CustomerDto(String firstName, String lastName, String phoneNumber, double ordersAmount, int discount) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.ordersAmount = ordersAmount;
		this.discount = discount;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public double getOrdersAmount() {
		return ordersAmount;
	}

	public void setOrdersAmount(double ordersAmount) {
		this.ordersAmount = ordersAmount;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}
}
