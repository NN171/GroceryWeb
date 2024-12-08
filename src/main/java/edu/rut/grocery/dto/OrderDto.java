package edu.rut.grocery.dto;

import edu.rut.grocery.domain.ProductOrder;

import java.util.List;
import java.util.Set;

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
