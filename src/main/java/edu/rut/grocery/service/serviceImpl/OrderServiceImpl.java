package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.domain.Order;
import edu.rut.grocery.dto.OrderDto;
import edu.rut.grocery.repository.OrderRepository;
import edu.rut.grocery.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

	@Override
	public List<OrderDto> getOrders(int page, int size) {

		Pageable pageable = PageRequest.of(page-1, size, Sort.by("createDate").ascending());
		Page<Order> orders = orderRepository.findAll(pageable);

		return orders.stream()
				.map(order -> modelMapper.map(order, OrderDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public OrderDto getOrder(Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Order not found"));

		return modelMapper.map(order, OrderDto.class);
	}

	@Override
	public String saveOrder(OrderDto orderDto) {
		Order order = modelMapper.map(orderDto, Order.class);
		orderRepository.save(order);

		return "Order saved";
	}

	@Override
	public String updateOrder(OrderDto orderDto, Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Order not found"));
		modelMapper.map(orderDto, order);
		orderRepository.save(order);
		return "Order updated";
	}
}
