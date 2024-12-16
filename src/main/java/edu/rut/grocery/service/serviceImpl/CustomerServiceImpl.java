package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.domain.Customer;
import edu.rut.grocery.domain.Order;
import edu.rut.grocery.dto.CustomerDto;
import edu.rut.grocery.repository.CustomerRepository;
import edu.rut.grocery.repository.OrderRepository;
import edu.rut.grocery.service.CustomerService;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final ModelMapper modelMapper;
	private final CustomerRepository customerRepository;
	private final OrderRepository orderRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, OrderRepository orderRepository) {
		this.customerRepository = customerRepository;
		this.modelMapper = modelMapper;
		this.orderRepository = orderRepository;
	}

	@Override
	@Cacheable(value = "getCustomers")
	public Page<CustomerDto> getCustomers(int page, int size) {

		Pageable pageable = PageRequest.of(page - 1, size, Sort.by("lastName").ascending());
		Page<Customer> customers = customerRepository.findAll(pageable);

		return new PageImpl<>(
				customers.getContent().stream()
						.map(element -> modelMapper.map(element, CustomerDto.class))
						.collect(Collectors.toList()
						),
				customers.getPageable(),
				customers.getTotalPages()
		);
	}

	@Override
	public double calculateOrders(Long id) {

		List<Order> orders = orderRepository.getOrdersByCustomerId(id);

		double counter = 0;
		for (Order order : orders) {
			counter += order.getPrice();
		}

		return counter;
	}

	@Override
	public int calculateDiscount(Long id) {

		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Customer not found"));

		double ordersAmount = customer.getOrdersAmount();
		int residue = (int) ordersAmount / 3000;

		return ordersAmount < 30000 ? residue : 10;
	}

	@Override
	@Cacheable(value = "getCustomer", key = "#id")
	public CustomerDto getCustomer(Long id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Customer not found"));

		return modelMapper.map(customer, CustomerDto.class);
	}

	@Override
	@CacheEvict(value = "getCustomers", allEntries = true)
	public String saveCustomer(CustomerDto customerDto) {
		Customer customer = modelMapper.map(customerDto, Customer.class);
		customerRepository.save(customer);

		return "Customer saved";
	}

	@Override
	@CacheEvict(value = "getCustomers", allEntries = true)
	public String deleteCustomer(Long id) {
		boolean removed = customerRepository.deleteById(id);
		if (!removed) throw new EntityNotFoundException("Customer not found");

		return "Success";
	}

	@Override
	@CacheEvict(value = "getCustomers", allEntries = true)
	public String updateCustomer(CustomerDto customerDto, Long id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Customer not found"));
		modelMapper.map(customerDto, customer);
		customerRepository.save(customer);
		return "Customer updated";
	}
}
