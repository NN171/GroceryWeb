package edu.rut.grocery.model.dto;

public class StoreDto {

	private String address;
	private int employeesNum;
	private double soldAmount;

	public StoreDto(String address, int employeesNum, double soldAmount) {
		this.address = address;
		this.employeesNum = employeesNum;
		this.soldAmount = soldAmount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getEmployeesNum() {
		return employeesNum;
	}

	public void setEmployeesNum(int employeesNum) {
		this.employeesNum = employeesNum;
	}

	public double getSoldAmount() {
		return soldAmount;
	}

	public void setSoldAmount(double soldAmount) {
		this.soldAmount = soldAmount;
	}
}
