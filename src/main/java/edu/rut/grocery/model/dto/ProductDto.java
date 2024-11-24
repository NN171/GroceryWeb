package edu.rut.grocery.model.dto;

import edu.rut.grocery.model.domain.Feedback;
import edu.rut.grocery.model.domain.ProductOrder;

import java.time.LocalDateTime;
import java.util.Set;

public record ProductDto(
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
