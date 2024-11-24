package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.domain.Order;
import edu.rut.grocery.dto.OrderDto;
import edu.rut.grocery.repository.OrderRepository;
import edu.rut.grocery.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

	private final ModelMapper modelMapper;
	private final OrderRepository orderRepository;

	public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper) {
		this.orderRepository = orderRepository;
		this.modelMapper = modelMapper;
	}

	public List<OrderDto> getOrders() {
		List<Order> orders = orderRepository.findAll()
				.orElseThrow(() -> new EntityNotFoundException("Orders not found"));

		return orders.stream()
				.map(order -> modelMapper.map(order, OrderDto.class))
				.collect(Collectors.toList());
	}

	public OrderDto getOrder(Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Order not found"));

		return modelMapper.map(order, OrderDto.class);
	}

	public String saveOrder(OrderDto orderDto) {
		Order order = modelMapper.map(orderDto, Order.class);
		orderRepository.save(order);

		return "Order saved";
	}

	public String updateOrder(OrderDto orderDto, Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Order not found"));
		modelMapper.map(orderDto, order);
		orderRepository.save(order);
		return "Order updated";
	}
}
