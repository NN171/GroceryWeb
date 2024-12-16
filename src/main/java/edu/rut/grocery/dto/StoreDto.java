package edu.rut.grocery.dto;

public class StoreDto {
	private Long id;
	private String address;
	private Integer employeesNum;
	private Double soldAmount;

	public StoreDto() {
	}

	public StoreDto(Long id, String address, Integer employeesNum, Double soldAmount) {
		this.id = id;
		this.address = address;
		this.employeesNum = employeesNum;
		this.soldAmount = soldAmount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getEmployeesNum() {
		return employeesNum;
	}

	public void setEmployeesNum(Integer employeesNum) {
		this.employeesNum = employeesNum;
	}

	public Double getSoldAmount() {
		return soldAmount;
	}

	public void setSoldAmount(Double soldAmount) {
		this.soldAmount = soldAmount;
	}
}
