package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.model.domain.Customer;
import edu.rut.grocery.model.dto.CustomerDto;
import edu.rut.grocery.repository.CustomerRepository;
import edu.rut.grocery.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final ModelMapper modelMapper;
	private final CustomerRepository customerRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
		this.customerRepository = customerRepository;
		this.modelMapper = modelMapper;
	}

	public List<CustomerDto> getCustomers() {
		List<Customer> customers = customerRepository.findAll()
				.orElseThrow(() -> new EntityNotFoundException("Customers not found"));

		return customers.stream()
				.map(customer -> modelMapper.map(customer, CustomerDto.class))
				.collect(Collectors.toList());
	}

	public CustomerDto getCustomer(Long id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Customer not found"));

		return modelMapper.map(customer, CustomerDto.class);
	}

	public String saveCustomer(CustomerDto customerDto) {
		Customer customer = modelMapper.map(customerDto, Customer.class);
		customerRepository.save(customer);

		return "Customer saved";
	}

	public String deleteCustomer(Long id) {
		boolean removed = customerRepository.deleteById(id);
		if (!removed) throw new EntityNotFoundException("Customer not found");

		return "Success";
	}

	public String updateCustomer(CustomerDto customerDto, Long id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Customer not found"));
		modelMapper.map(customerDto, customer);
		customerRepository.save(customer);
		return "Customer updated";
	}
}
