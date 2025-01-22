package edu.rut.grocery.service;

import edu.rut.grocery.dto.OrderDto;
import org.springframework.data.domain.Page;

public interface OrderService {

	Page<OrderDto> getOrders(int page, int size);

	OrderDto getActiveOrder(Long id);

	void saveOrder(OrderDto orderDto);
}
