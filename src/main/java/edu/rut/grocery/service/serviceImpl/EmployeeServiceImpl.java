package edu.rut.grocery.service.serviceImpl;

import edu.rut.grocery.domain.Employee;
import edu.rut.grocery.domain.Store;
import edu.rut.grocery.dto.EmployeeDto;
import edu.rut.grocery.repository.EmployeeRepository;
import edu.rut.grocery.repository.StoreRepository;
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

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final ModelMapper modelMapper;
	private final EmployeeRepository employeeRepository;
	private final StoreRepository storeRepository;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper, StoreRepository storeRepository) {
		this.employeeRepository = employeeRepository;
		this.modelMapper = modelMapper;
		this.storeRepository = storeRepository;
	}

	@Override
	@Cacheable("getEmployees")
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
	@Cacheable(value = "getEmployee", key = "#id")
	public EmployeeDto getEmployee(Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Employee not found"));

		return modelMapper.map(employee, EmployeeDto.class);
	}

	@Override
	@CacheEvict(value = "getEmployees", allEntries = true)
	public Long saveEmployee(EmployeeDto employeeDto) {
		Employee employee = modelMapper.map(employeeDto, Employee.class);
		Store store = storeRepository.findByAddress(employeeDto.getAddress())
				.orElseThrow(() -> new EntityNotFoundException("Store not found"));
		employee.setStore(store);

		int employees = store.getEmployeesNum();
		store.setEmployeesNum(++employees);

		storeRepository.save(store);


		return employeeRepository.save(employee).getId();
	}

	@Override
	@CacheEvict(value = "getEmployees", allEntries = true)
	public void deleteEmployee(Long id) {
		employeeRepository.deleteById(id);
	}

	@Override
	@CacheEvict(value = "getEmployees", allEntries = true)
	public void updateEmployee(EmployeeDto employeeDto, Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Employee not found"));
		modelMapper.map(employeeDto, employee);
		Store store = storeRepository.findByAddress(employeeDto.getAddress())
				.orElseThrow(() -> new EntityNotFoundException("Store not found"));
		employee.setStore(store);
		employeeRepository.save(employee);
	}
}
