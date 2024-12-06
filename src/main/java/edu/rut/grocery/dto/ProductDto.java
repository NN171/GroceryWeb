package edu.rut.grocery.dto;

import edu.rut.grocery.domain.Feedback;
import edu.rut.grocery.domain.ProductOrder;

import java.time.LocalDateTime;
import java.util.Set;

public record ProductDto(
		Long id,
		String name,
		double price,
		int amount,
		LocalDateTime prodDate,
		LocalDateTime expiryDate,
		double avgRating,
		Set<Feedback>feedbacks,
		Set<ProductOrder> productOrders
) {
}
