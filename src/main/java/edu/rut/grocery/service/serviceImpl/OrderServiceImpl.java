package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.repository.OrderRepository;
import edu.rut.grocery.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;

	public OrderServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
}
