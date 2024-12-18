package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.domain.Employee;
import edu.rut.grocery.dto.EmployeeDto;
import edu.rut.grocery.repository.EmployeeRepository;
import edu.rut.grocery.service.EmployeeService;
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

import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final ModelMapper modelMapper;
	private final EmployeeRepository employeeRepository;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
		this.employeeRepository = employeeRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public Page<EmployeeDto> getEmployees(int page, int size) {

		Pageable pageable = PageRequest.of(page - 1, size, Sort.by("lastName").ascending());
		Page<Employee> employees = employeeRepository.findAll(pageable);

		return new PageImpl<>(
				employees.getContent().stream()
						.map(element -> modelMapper.map(element, EmployeeDto.class))
						.toList(),
				employees.getPageable(),
				employees.getTotalPages()
		);
	}

	@Override
	@Cacheable("getEmployee")
	public EmployeeDto getEmployee(Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Employee not found"));

		return modelMapper.map(employee, EmployeeDto.class);
	}

	@Override
	@CacheEvict("saveEmployee")
	public String saveEmployee(EmployeeDto employeeDto) {
		Employee employee = modelMapper.map(employeeDto, Employee.class);
		employeeRepository.save(employee);

		return "employee saved";
	}

	@Override
	public String deleteEmployee(Long id) {
		boolean removed = employeeRepository.deleteById(id);
		if (!removed) throw new EntityNotFoundException("Employee not found");

		return "Success";
	}

	@Override
	@CacheEvict("updateEmployee")
	public String updateEmployee(EmployeeDto employeeDto, Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Employee not found"));
		modelMapper.map(employeeDto, employee);
		employeeRepository.save(employee);
		return "employee updated";
	}
}
