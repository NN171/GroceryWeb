package edu.rut.grocery.dto;

public record ProductDto(
		Long id,
		String name,
		Double price,
		Integer amount,
		String prodDate,
		String expiryDate,
		Double avgRating
) {
}
