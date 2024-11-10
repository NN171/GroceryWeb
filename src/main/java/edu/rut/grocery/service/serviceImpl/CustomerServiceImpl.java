package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.repository.CustomerRepository;
import edu.rut.grocery.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}


}
