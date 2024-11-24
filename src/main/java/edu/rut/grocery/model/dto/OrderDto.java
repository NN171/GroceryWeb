package edu.rut.grocery.model.dto;

import edu.rut.grocery.model.domain.ProductOrder;

import java.util.Set;

public record OrderDto(
		double price,
		Set<ProductOrder>productOrders
) {
}
