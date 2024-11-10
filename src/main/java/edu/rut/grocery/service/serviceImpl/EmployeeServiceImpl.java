package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.repository.CustomerRepository;
import edu.rut.grocery.repository.EmployeeRepository;
import edu.rut.grocery.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

}
