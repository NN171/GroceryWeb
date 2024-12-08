package edu.rut.grocery.service;

import edu.rut.grocery.dto.CustomerDto;
import org.springframework.data.domain.Page;

public interface CustomerService {

	Page<CustomerDto> getCustomers(int page, int size);

	CustomerDto getCustomer(Long id);

	String saveCustomer(CustomerDto customerDto);

	String deleteCustomer(Long id);

	String updateCustomer(CustomerDto customerDto, Long id);

	double calculateOrders(Long id);

	int calculateDiscount(Long id);
}
