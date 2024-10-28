package edu.rut.grocery.repository;

import edu.rut.grocery.model.domain.Employee;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends BaseRepository<Employee, Long> {
}
