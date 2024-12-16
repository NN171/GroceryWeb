package edu.rut.grocery.service;

import edu.rut.grocery.domain.Order;

public interface BasketService {

	Order addProduct(Long customerId, Long productId, int quantity);

	Order removeProduct(Long orderId, Long productId);
}
