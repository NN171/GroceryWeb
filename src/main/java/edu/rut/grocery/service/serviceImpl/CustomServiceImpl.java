package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.domain.Customer;
import edu.rut.grocery.domain.Feedback;
import edu.rut.grocery.domain.Order;
import edu.rut.grocery.domain.Product;
import edu.rut.grocery.domain.ProductOrder;
import edu.rut.grocery.dto.HighRatedDto;
import edu.rut.grocery.dto.ProductDto;
import edu.rut.grocery.repository.CustomerRepository;
import edu.rut.grocery.repository.FeedbackRepository;
import edu.rut.grocery.repository.ProductRepository;
import edu.rut.grocery.service.CustomService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomServiceImpl implements CustomService {

	private final FeedbackRepository feedbackRepository;
	private final ProductRepository productRepository;
	private final ModelMapper modelMapper;
	private final CustomerRepository customerRepository;


	public CustomServiceImpl(FeedbackRepository feedbackRepository,
							 ProductRepository productRepository,
							 ModelMapper modelMapper,
							 CustomerRepository
									 customerRepository) {
		this.feedbackRepository = feedbackRepository;
		this.productRepository = productRepository;
		this.modelMapper = modelMapper;
		this.customerRepository = customerRepository;
	}

	@Override
	public List<HighRatedDto> getHighRatedProducts() {

		List<Product> products = productRepository.findAll();

		for (Product product : products) {

			Set<Feedback> feedbacks = product.getFeedbacks();
			double avgRating = 0;

			for (Feedback feedback : feedbacks) {
				avgRating += feedback.getRating();
			}

			product.setAvgRating(avgRating / feedbacks.size());
			productRepository.save(product);
		}

		Pageable pageable = PageRequest.of(0, 10, Sort.by("avgRating").descending());
		Page<Product> highRatedPage = productRepository.findAll(pageable);

		List<Product> highRatedProducts = highRatedPage.getContent();

		return highRatedProducts.stream()
				.map(p -> modelMapper.map(p, HighRatedDto.class))
				.toList();
	}

	@Override
	public List<ProductDto> alwaysOrdering(Long id) {

		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Customer not found"));

		Set<Order> orders = customer.getOrders();

		if (orders.isEmpty()) throw new IllegalArgumentException("Orders not found");

		Map<Product, Integer> productMap = new HashMap<>();

		for (Order order : orders) {
			Set<ProductOrder> productOrders = order.getProductOrders();

			for (ProductOrder productOrder : productOrders) {

				Product product = productOrder.getProduct();
				int quantity = productOrder.getQuantity();

				productMap.merge(product, quantity, Integer::sum);
			}
		}

		List<Product> products = new ArrayList<>(productMap.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.limit(10)
				.map(Map.Entry::getKey)
				.toList());

		return products.stream()
				.map(p -> modelMapper.map(p, ProductDto.class))
				.collect(Collectors.toList());
	}
}
