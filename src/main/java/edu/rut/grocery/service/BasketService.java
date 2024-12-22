package edu.rut.grocery.service;

import edu.rut.grocery.domain.Order;
import edu.rut.grocery.dto.OrderDto;

public interface BasketService {

	OrderDto addProduct(Long customerId, Long productId, int quantity);

	Order removeProduct(Long orderId, Long productId);
}
