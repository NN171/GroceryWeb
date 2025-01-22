package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.domain.Customer;
import edu.rut.grocery.domain.Order;
import edu.rut.grocery.domain.Product;
import edu.rut.grocery.domain.ProductOrder;
import edu.rut.grocery.domain.Status;
import edu.rut.grocery.dto.OrderDto;
import edu.rut.grocery.repository.CustomerRepository;
import edu.rut.grocery.repository.OrderRepository;
import edu.rut.grocery.repository.ProductOrderRepository;
import edu.rut.grocery.repository.ProductRepository;
import edu.rut.grocery.service.BasketService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

@Service
public class BasketServiceImpl implements BasketService {

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final ProductOrderRepository productOrderRepository;
	private final CustomerRepository customerRepository;
	private final ModelMapper modelMapper;


	public BasketServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, ProductOrderRepository productOrderRepository, CustomerRepository customerRepository, ModelMapper modelMapper) {
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
		this.productOrderRepository = productOrderRepository;
		this.customerRepository = customerRepository;
		this.modelMapper = modelMapper;
	}

	@Transactional
	@Caching(evict = {
			@CacheEvict(value = "getProducts", allEntries = true),
			@CacheEvict(value = "getOrder", key = "#result.id")
	})
	@Override
	public OrderDto addProduct(Long customerId, Long productId, int quantity) {

		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));

		if (product.getAmount() < quantity) {
			throw new RuntimeException("Not enough products in store");
		}

		Order order = orderRepository.getByCustomerIdAndStatus(customerId, Status.IN_PROGRESS);

		if (order == null) {
			order = createNewOrder(customer);
		}

		Optional<ProductOrder> existingProductOrder = order.getProductOrders().stream()
				.filter(po -> po.getProduct().equals(product))
				.findFirst();

		if (existingProductOrder.isPresent()) {

			ProductOrder productOrder = existingProductOrder.get();
			productOrder.setQuantity(productOrder.getQuantity() + quantity);
			productOrder.setPrice(product.getPrice()*productOrder.getQuantity());
		} else {

			ProductOrder productOrder = new ProductOrder();
			productOrder.setOrder(order);
			productOrder.setProduct(product);
			productOrder.setQuantity(quantity);
			productOrder.setPrice(product.getPrice()*productOrder.getQuantity());
			order.getProductOrders().add(productOrder);

			productOrderRepository.save(productOrder);
		}

		updatePrice(order);

		product.setAmount(product.getAmount() - quantity);
		productRepository.save(product);

		return modelMapper.map(orderRepository.save(order), OrderDto.class);
	}

	@Transactional
	@Caching(evict = {
			@CacheEvict(value = "getProducts", allEntries = true),
			@CacheEvict(value = "getOrder", key = "#customerId")
	})
	@Override
	public Order removeProduct(Long customerId, Long productId) {

		Order order = orderRepository.getByCustomerIdAndStatus(customerId, Status.IN_PROGRESS);

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product doesn't exist"));

		ProductOrder productOrder = productOrderRepository.getByOrderAndProduct(order, product)
				.orElseThrow(() -> new RuntimeException("Basket is empty"));

		order.getProductOrders().remove(productOrder);
		product.setAmount(product.getAmount() + productOrder.getQuantity());

		productOrderRepository.delete(productOrder);
		productRepository.save(product);
		updatePrice(order);

		return orderRepository.save(order);
	}

	private void updatePrice(Order order) {

		double price = order.getProductOrders().stream()
				.mapToDouble(po -> po.getProduct().getPrice() * po.getQuantity())
				.sum();

		order.setPrice(price);
	}

	private Order createNewOrder(Customer customer) {
		Order order = new Order();
		order.setPrice(0);
		order.setCreateDate(LocalDate.now());
		order.setStatus(Status.IN_PROGRESS);
		order.setCustomer(customer);
		order.setProductOrders(new HashSet<>());

		return orderRepository.save(order);
	}
}
