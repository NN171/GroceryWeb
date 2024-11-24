package edu.rut.grocery.service;

import edu.rut.grocery.dto.OrderDto;

import java.util.List;

public interface OrderService {

	List<OrderDto> getOrders();

	OrderDto getOrder(Long id);

	String saveOrder(OrderDto orderDto);

	String updateOrder(OrderDto orderDto, Long id);
}
