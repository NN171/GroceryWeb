package edu.rut.grocery.service;

import edu.rut.grocery.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

	List<EmployeeDto> getEmployees();

	EmployeeDto getEmployee(Long id);

	String saveEmployee(EmployeeDto employeeDto);

	String deleteEmployee(Long id);

	String updateEmployee(EmployeeDto employeeDto, Long id);
}
