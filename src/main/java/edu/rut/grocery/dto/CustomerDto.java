package edu.rut.grocery.dto;

public class CustomerDto {
	private Long id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private Double ordersAmount;
	private Integer discount;

	public CustomerDto() {
	}

	public CustomerDto(Long id, String firstName, String lastName, String phoneNumber, Double ordersAmount, Integer discount) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.ordersAmount = ordersAmount;
		this.discount = discount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Double getOrdersAmount() {
		return ordersAmount;
	}

	public void setOrdersAmount(Double ordersAmount) {
		this.ordersAmount = ordersAmount;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
}
