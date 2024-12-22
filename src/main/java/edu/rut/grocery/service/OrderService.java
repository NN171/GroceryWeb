package edu.rut.grocery.service;

import edu.rut.grocery.dto.OrderDto;
import org.springframework.data.domain.Page;

public interface OrderService {

	Page<OrderDto> getOrders(int page, int size);

	OrderDto getActiveOrder(Long id);

	String saveOrder(OrderDto orderDto);
}
