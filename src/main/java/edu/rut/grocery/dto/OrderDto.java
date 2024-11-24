package edu.rut.grocery.dto;

import edu.rut.grocery.domain.ProductOrder;

import java.util.Set;

public record OrderDto(
		double price,
		Set<ProductOrder>productOrders
) {
}
