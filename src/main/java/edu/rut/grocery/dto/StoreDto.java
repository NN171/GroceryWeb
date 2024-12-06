package edu.rut.grocery.dto;

public record StoreDto(
		Long id,
		String address,
		int employeesNum,
		double soldAmount
) {
}
