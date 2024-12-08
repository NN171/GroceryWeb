package edu.rut.grocery.dto;

import java.util.List;

public record OrderDto(
		Long id,
		String status,
		Double price,
		Long customer,
		Long employee,
		List<OrderProductDto> products
) {
	public record OrderProductDto(
			Long productId,
			Integer quantity,
			Double price
	) {
	}
}
