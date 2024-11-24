package edu.rut.grocery.dto;

public record CustomerDto(
		String firstName,
		String lastName,
		String phoneNumber,
		double ordersAmount,
		int discount
) {
}
