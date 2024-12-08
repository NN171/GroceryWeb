package edu.rut.grocery.dto;

public record StoreDto(
		Long id,
		String address,
		Integer employeesNum,
		Double soldAmount
) {
}
