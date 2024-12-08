package edu.rut.grocery.service;

import edu.rut.grocery.domain.Order;
import edu.rut.grocery.dto.OrderDto;
import org.springframework.data.domain.Page;

public interface OrderService {

	Page<OrderDto> getOrders(int page, int size);

	OrderDto getOrder(Long id);

	String saveOrder(OrderDto orderDto);

	String updateOrder(OrderDto orderDto, Long id);

	Order addProduct(Long orderId, Long productId, int quantity);
}
