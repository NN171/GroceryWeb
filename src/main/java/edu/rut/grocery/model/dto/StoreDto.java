package edu.rut.grocery.model.dto;

public record StoreDto(
		String address,
		int employeesNum,
		double soldAmount
) {
}
