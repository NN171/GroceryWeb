package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.domain.Order;
import edu.rut.grocery.dto.OrderDto;
import edu.rut.grocery.dto.ProductOrderDto;
import edu.rut.grocery.repository.OrderRepository;
import edu.rut.grocery.repository.ProductRepository;
import edu.rut.grocery.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

	private final ModelMapper modelMapper;
	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;

	public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, ProductRepository productRepository) {
		this.orderRepository = orderRepository;
		this.modelMapper = modelMapper;
		this.productRepository = productRepository;
	}

	@Override
	@Cacheable("getOrders")
	public Page<OrderDto> getOrders(int page, int size) {

		Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createDate").ascending());
		Page<Order> orders = orderRepository.findAll(pageable);

		return new PageImpl<>(
				orders.getContent().stream()
						.map(element -> modelMapper.map(element, OrderDto.class))
						.collect(Collectors.toList()
						),
				orders.getPageable(),
				orders.getTotalPages()
		);
	}

	@Override
	@Cacheable(value = "getOrder", key = "#id")
	public OrderDto getActiveOrder(Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Order not found"));

		return modelMapper.map(order, OrderDto.class);
	}

	@Override
	@CacheEvict(value = "getOrders", allEntries = true)
	public void saveOrder(OrderDto orderDto) {

		double orderCost = 0;

		for (ProductOrderDto product : orderDto.getProductOrders()) {

			orderCost += product.getPrice() * product.getQuantity();
		}

		Order order = modelMapper.map(orderDto, Order.class);
		int discount = order.getCustomer().getDiscount();

		order.setPrice(orderCost * discount * 0.01);

		orderRepository.save(order);
	}
}
