package edu.rut.grocery.service;

import edu.rut.grocery.dto.CustomerDto;

import java.util.List;

public interface CustomerService {

	List<CustomerDto> getCustomers(int page, int no);

	CustomerDto getCustomer(Long id);

	String saveCustomer(CustomerDto customerDto);

	String deleteCustomer(Long id);

	String updateCustomer(CustomerDto customerDto, Long id);
}
