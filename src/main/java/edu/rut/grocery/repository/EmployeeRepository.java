package edu.rut.grocery.repository;

import edu.rut.grocery.domain.Employee;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends BaseRepository<Employee, Long> {

	void deleteById(Long id);
}
