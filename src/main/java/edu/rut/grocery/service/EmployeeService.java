package edu.rut.grocery.service;

import edu.rut.grocery.dto.EmployeeDto;
import org.springframework.data.domain.Page;

public interface EmployeeService {

	Page<EmployeeDto> getEmployees(int page, int size);

	EmployeeDto getEmployee(Long id);

	Long saveEmployee(EmployeeDto employeeDto);

	void deleteEmployee(Long id);

	void updateEmployee(EmployeeDto employeeDto, Long id);
}
