package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.domain.Customer;
import edu.rut.grocery.domain.Order;
import edu.rut.grocery.domain.Product;
import edu.rut.grocery.domain.ProductOrder;
import edu.rut.grocery.domain.Status;
import edu.rut.grocery.repository.CustomerRepository;
import edu.rut.grocery.repository.OrderRepository;
import edu.rut.grocery.repository.ProductOrderRepository;
import edu.rut.grocery.repository.ProductRepository;
import edu.rut.grocery.service.BasketService;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BasketServiceImpl implements BasketService {

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final ProductOrderRepository productOrderRepository;
	private final CustomerRepository customerRepository;


	public BasketServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, ProductOrderRepository productOrderRepository, CustomerRepository customerRepository) {
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
		this.productOrderRepository = productOrderRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public Order addProduct(Long customerId, Long productId, int quantity) {

		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));

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
		} else {

			ProductOrder productOrder = new ProductOrder();
			productOrder.setOrder(order);
			productOrder.setProduct(product);
			productOrder.setQuantity(quantity);
			order.getProductOrders().add(productOrder);
		}

		order.setPrice(calculatePrice(order));

		product.setAmount(product.getAmount() - quantity);
		productRepository.save(product);

		return orderRepository.save(order);
	}

	@Override
	public Order removeProduct(Long orderId, Long productId) {

		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order doesn't exist"));

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product doesn't exist"));

		ProductOrder productOrder = productOrderRepository.getByOrderAndProduct(order, product)
				.orElseThrow(() -> new RuntimeException("Basket is empty"));

		order.getProductOrders().remove(productOrder);
		product.setAmount(product.getAmount() + productOrder.getQuantity());

		productOrderRepository.delete(productOrder);
		productRepository.save(product);
		order.setPrice(calculatePrice(order));

		return orderRepository.save(order);
	}

	private double calculatePrice(Order order) {

		return order.getProductOrders().stream()
				.mapToDouble(po -> po.getProduct().getPrice() * po.getQuantity())
				.sum();
	}

	private Order createNewOrder(Customer customer) {
		Order order = new Order();
		order.setCustomer(customer);
		order.setStatus(Status.IN_PROGRESS);
		order.setCreateDate(LocalDate.now());
		order.setPrice(0);

		return orderRepository.save(order);
	}
}
