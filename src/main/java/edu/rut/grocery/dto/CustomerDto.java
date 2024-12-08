package edu.rut.grocery.dto;

public record CustomerDto(
		Long id,
		String firstName,
		String lastName,
		String phoneNumber,
		Double ordersAmount,
		Integer discount
) {
}
